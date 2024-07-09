package com.workflowManager.controller.boardcontoller;

import com.workflowManager.common.boardpage.PageMaker;
import com.workflowManager.common.boardpage.Search;
import com.workflowManager.dto.boarddto.BoardDetailDto;
import com.workflowManager.dto.boarddto.BoardListDto;
import com.workflowManager.dto.boarddto.BoardUpdateDto;
import com.workflowManager.dto.boarddto.BoardWriteDto;
import com.workflowManager.service.boardservice.BoardService;
import com.workflowManager.util.LoginUtil;
import com.workflowManager.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Search search,
                       Model model,
                       HttpSession session) {
        try {
            if (search.getType() != null && search.getType().equals("id")) {
                search.setKeyword(LoginUtil.getLoggedInUserAccount(session));
            }

            List<BoardListDto> boardList = boardService.findAll(search);

            // 검색 조건에 따른 게시물 수를 계산합니다.
            int totalCount = boardService.boardListCount(search);
            PageMaker maker = new PageMaker(search, totalCount);

            log.info("Total Count: {}", totalCount);

            model.addAttribute("boards", boardList);
            model.addAttribute("maker", maker);

            return "board/boardList";

        } catch (Exception e) {
            log.error("Error in list method: ", e);
            return "error";
        }

    }

    @GetMapping("/write")
    public String write() {

        return "board/boardWrite";
    }

    @PostMapping("/write")
    public String register(BoardWriteDto dto, HttpSession session) {
        try {
            log.info("Received DTO: {}", dto);

            if (boardService.save(dto, session)) {
                return "redirect:/board/list";
            } else {
                return "error";
            }
        } catch (Exception e) {
            log.error("Error in register method: ", e);
            return "error";
        }
    }

    @GetMapping("/detail")
    public String findOne(@RequestParam("bno") int boardId,
                          Model model,
                          HttpServletRequest request,
                          HttpSession session) {
        try {
            // 조회수 증가 처리
            boardService.updateViewCount(boardId, session);

            BoardDetailDto board = boardService.findOne(boardId);

            String account = LoginUtil.getLoggedInUserAccount(session);

            model.addAttribute("board", board);
            model.addAttribute("id", account);

            // 게시물 조회를 누를때 주소값을 저장해서 목록으로 돌아갈때 다시 리다이렉트
            String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("/list")) {
                request.getSession().setAttribute("referer", referer);
            }
        
            log.info("referer: {}", referer);
            log.info("account: {}", account);

            return "board/boardDetail";
        } catch (Exception e) {
            log.error("Error in findOne method: ", e);
            return "error";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno") int boardId) {
        try {
            BoardDetailDto board = boardService.findOne(boardId);

            int targetId = board.getBoardId();

            boardService.delete(targetId);

            return "redirect:/board/list";
        } catch (Exception e) {
            log.error("Error in delete method: ", e);
            return "error";
        }
    }

    @GetMapping("/pwcheck")
    public String PasswordCheck(HttpSession session,
                                @RequestParam("action") String action,
                                @RequestParam("bno") int boardId) {
        
        try {

            if (LoginUtil.isAdmin(session) && "delete".equals(action)) {
                return "redirect:/board/delete?bno=" + boardId;
            }

            return "board/boardPwCheck";

        } catch (Exception e) {

            log.error("Error in PasswordCheck method: ", e);

            return "error";

        }
    }

    @PostMapping("/pwcheck")
    public String isEqualPw(@RequestParam("bno") int boardId,
                            @RequestParam("action") String action,
                            @RequestParam("boardPassword") String inputPassword,
                            HttpSession session,
                            Model model) {
        try {

            BoardDetailDto board = boardService.findOne(boardId);

            log.info(action);

            // 해싱된 비밀번호 검증
            if (PasswordUtil.checkPassword(inputPassword, board.getBoardPassword())) {
                if ("update".equals(action)) {
                    return "redirect:/board/update?bno=" + boardId;
                } else if ("delete".equals(action)) {
                    return "redirect:/board/delete?bno=" + boardId;
                }
            }

            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");

            return "board/boardPwCheck";
        } catch (Exception e) {

            log.error("Error in isEqualPw method: ", e);

            model.addAttribute("error", "오류가 발생했습니다.");

            return "board/boardPwCheck";
        }
    }

    @GetMapping("/update")
    public String modify(@RequestParam("bno") int boardId,
                         Model model,
                         HttpSession session) {
        try {
            BoardDetailDto board = boardService.findOne(boardId);

            model.addAttribute("board", board);

            return "board/boardUpdate";
        } catch (Exception e) {
            log.error("Error in modify method: ", e);
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam("bno") int boardId,
                         BoardUpdateDto dto) {
        try {
            log.info("Received DTO: {}", dto);

            boardService.update(dto);

            return "redirect:/board/detail?bno=" + boardId;
        } catch (Exception e) {
            log.error("Error in update method: ", e);
            return "error";
        }
    }

    @PostMapping("/upLike")
    @ResponseBody
    public ResponseEntity<?> upLikeCount(@RequestParam(value = "bno") Integer boardId,
                                         HttpSession session) {

        if (boardId == null) {
            return ResponseEntity
                    .badRequest()
                    .body("boardId parameter is required.");
        }

        if (!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("로그인이 필요합니다.");
        }

        try {
            boardService.upLikeCount(boardId);
            return ResponseEntity
                    .ok()
                    .body(boardService.findOne(boardId));
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating like count.");
        }
    }

    @PostMapping("/downLike")
    @ResponseBody
    public ResponseEntity<?> downLikeCount(@RequestParam(value = "bno") Integer boardId,
                                           HttpSession session) {

        if (boardId == null) {
            return ResponseEntity
                    .badRequest()
                    .body("boardId parameter is required.");
        }

        try {
            boardService.downLikeCount(boardId);
            return ResponseEntity
                    .ok()
                    .body(boardService.findOne(boardId));
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating like count.");
        }
    }

    @PostMapping("/upDislike")
    @ResponseBody
    public ResponseEntity<?> upDislikeCount(@RequestParam(value = "bno") Integer boardId) {

        if (boardId == null) {
            return ResponseEntity.badRequest().body("boardId parameter is required.");
        }

        try {
            boardService.upDislikeCount(boardId);
            return ResponseEntity
                    .ok()
                    .body(boardService.findOne(boardId));
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating dislike count.");
        }
    }

    @PostMapping("/downDislike")
    @ResponseBody
    public ResponseEntity<?> downDislikeCount(@RequestParam(value = "bno") Integer boardId) {

        if (boardId == null) {
            return ResponseEntity
                    .badRequest()
                    .body("boardId parameter is required.");
        }

        try {
            boardService.downDislikeCount(boardId);

            return ResponseEntity
                    .ok()
                    .body(boardService.findOne(boardId));

        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating dislike count.");

        }
    }


}
