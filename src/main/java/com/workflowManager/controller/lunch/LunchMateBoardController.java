package com.workflowManager.controller.lunch;

import com.workflowManager.dto.lunchBoardDto.LunchBoardFindAllDto;
import com.workflowManager.dto.lunchBoardDto.LunchMemberDto;
import com.workflowManager.entity.LunchMateBoard;
import com.workflowManager.entity.User;
import com.workflowManager.service.lunchService.LunchMateBoardService;
import com.workflowManager.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lunchMateBoard")
public class LunchMateBoardController {

    @Autowired
    private LunchMateBoardService lunchMateBoardService;


    @Autowired
    public LunchMateBoardController(LunchMateBoardService lunchMateBoardService) {
        this.lunchMateBoardService = lunchMateBoardService;
    }

    // 기본 경로 매핑 추가
    @GetMapping
    public String defaultPage() {
        return "redirect:/lunchMateBoard/list";
    }

    // 게시판 목록 페이지
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(name = "page", defaultValue = "1") int pageNo,
                       @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        // 전체 게시물 수 조회
        int totalCount = lunchMateBoardService.getTotalCount();

        // 페이징 처리된 게시물 목록 조회
        List<LunchMateBoard> boards = lunchMateBoardService.findPagedBoards(pageNo, pageSize);

        // LunchMateBoard를 LunchListDto로 변환
        List<LunchBoardFindAllDto> boardDTOs = boards.stream()
                .map(LunchMateBoard::toDto)
                .collect(Collectors.toList());

        // 페이징 관련 정보 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int startPage = Math.max(1, pageNo - 5);
        int endPage = Math.min(startPage + 9, totalPages);

        model.addAttribute("boards", boardDTOs);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        return "lunch/lunchboard"; // src/main/webapp/WEB-INF/views/lunch/lunchboard.jsp
    }

    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser != null) {
            model.addAttribute("board", new LunchMateBoard());
            model.addAttribute("loggedInUser", currentUser); // 현재 로그인된 사용자 정보 추가
            return "lunch/createLunchBoard"; // src/main/webapp/WEB-INF/views/lunch/createLunchBoard.jsp
        } else {
            return "redirect:/login"; // 로그인되어 있지 않은 경우 로그인 페이지로 리다이렉트
        }
    }

    // 글 작성 처리
    @PostMapping("/new")
    public String create(@ModelAttribute("board") LunchMateBoard board, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        String userId = LoginUtil.getLoggedInUser(session).getUserId();
        boolean loggedIn = LoginUtil.isLoggedIn(session);
        System.out.println(loggedIn);
        if (loggedIn) {
            board.setUserId(userId);  // 현재 로그인한 사용자의 userId를 설정
            lunchMateBoardService.save(board, userId);
            return "redirect:/lunchMateBoard/list"; // 다시 목록 페이지로 리다이렉트
        } else {
            return "redirect:/login"; // 로그인되어 있지 않은 경우 로그인 페이지로 리다이렉트
        }
    }



    @PostMapping("/joinLunch")
    @ResponseBody
    public ResponseEntity<Object> joinLunch(@RequestBody LunchMemberDto lunchMemberDto) {
        // boardDto를 이용하여 필요한 비즈니스 로직을 수행합니다
//
//        System.out.println("lunchMemberDto = " + lunchMemberDto);
//        if (lunchMemberDto.getLunchPostNumber() == null) {
//            return ResponseEntity.badRequest().body("boardId parameter is required.");
        System.out.println("👀👀👀1");

        System.out.println("👀👀👀2");
        lunchMateBoardService.incrementProgressStatus(Integer.parseInt(lunchMemberDto.getLunchPostNumber()));
        System.out.println("👀👀👀3");
        LunchMateBoard board = lunchMateBoardService.findOne(Integer.parseInt(lunchMemberDto.getLunchPostNumber()));
        System.out.println("👀4board = " + board);
//        }
        try {
        // 업데이트된 정보를 JSON으로 포장하여 클라이언트에게 반환
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("board", board); // 업데이트된 lunchMateBoard 정보를 포함

        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //
        // lunchMateBoardService.incrementProgressStatus(boardDto.getPostId());

//            return ResponseEntity
//                    .ok()
//                    .body(lunchMateBoardService.findOne(Integer.parseInt(lunchMemberDto.getLunchPostNumber())));
//
//        } catch (Exception e) {
//            // 예외 처리
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while updating dislike count.");
//
//        }
        //

        // 리디렉션할 URL을 리턴합니다 (예: 루트 경로로 리디렉션)
    }
}


//    // 글 작성 처리
//    @PostMapping("/new")
//    public String create(@ModelAttribute("board") LunchMateBoard board) {
//        // 현재 시간을 작성 시간으로 설정
//        board.setLunchDate(LocalDateTime.now());
//        board.setProgressStatus("준비");
//        board.setEatTime(LocalDateTime.now().toString());
//        lunchMateBoardService.save(board);
//        return "redirect:/lunchMateBoard/list"; // 다시 목록 페이지로 리다이렉트
//    } 20240629 수정

//    // 글 작성 처리
//    @PostMapping("/new")
//    public String create(@ModelAttribute("board") LunchMateBoard board) {
//        lunchMateBoardService.save(board);
//        return "redirect:/lunchMateBoard/list"; // 다시 목록 페이지로 리다이렉트
//    }


//    // 게시글 상세보기
//    @GetMapping("/view")
//    public String view(@RequestParam("lunchPostNumber") int lunchPostNumber, Model model) {
//        LunchMateBoard board = lunchMateBoardService.findOne(lunchPostNumber);
//        model.addAttribute("board", board);
//        return "lunch/viewLunchBoard"; // src/main/webapp/WEB-INF/views/lunch/viewLunchBoard.jsp
//    }
