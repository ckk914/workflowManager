package com.workflowManager.controller.boardcontoller;

import com.workflowManager.common.boardpage.Page;
import com.workflowManager.dto.replydto.*;
import com.workflowManager.service.boardservice.ReplyService;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ReplyController {

    private final ReplyService replyService;


    // 댓글 목록 조회
    @GetMapping("/{bno}/page/{pageNo}")
    public ResponseEntity<?> replies(@PathVariable("bno") int boardId,
                                     @PathVariable("pageNo") int pageNo,
                                     HttpSession session) {

        log.info("/reply/{} : GET", boardId);


        ReplyListDto replies = replyService.getReplies(boardId, new Page(pageNo, 10));

        replies.setLoginUser(LoginUtil.getLoggedInUser(session));

        return ResponseEntity
                .ok()
                .body(replies);
    }

    // 댓글 생성 요청
    @PostMapping
    public ResponseEntity<?> saveReply(@RequestBody @Valid ReplyWriteDto dto,
                                       HttpSession session) {

        log.info("ReplyWriteDto: {}", dto);

        // flag를 통하여 에러 검사
        boolean flag = replyService.save(dto, session);

        if (flag) {
            return ResponseEntity
                    .ok()
                    .body(replyService.getReplies(dto.getBoardId(), new Page(1, 10)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 생성에 실패했습니다.");
        }
    }

    // 댓글 수정 요청
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> updateReply(@RequestBody @Valid ReplyUpdateDto dto) {

        log.info("ReplyUpdateDto: {}", dto);

        boolean flag = replyService.update(dto);

        if (flag) {
            return ResponseEntity
                    .ok()
                    .body(replyService.getReplies(dto.getBoardId(), new Page(1, 10)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 업데이트에 실패했습니다.");
        }

    }

    // 댓글 삭제 요청
    @DeleteMapping
    public ResponseEntity<?> deleteReply(@RequestBody ReplyDeleteDto dto,
                                         HttpSession session) {

        log.info("ReplyDeleteDto: {}", dto);

        boolean flag = replyService.delete(dto,session);

        if (flag) {
            return ResponseEntity
                    .ok()
                    .body(replyService.getReplies(dto.getBoardId(), new Page(1, 10)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 삭제에 실패했습니다.");
        }

    }

    // 대댓글 목록 조회
    @GetMapping("/sub/{rno}")
    public ResponseEntity<?> subReplies(@PathVariable("rno") int replyId, HttpSession session) {

        log.info("/sub/{} : GET", replyId);

        SubReplyListDto subReplies = replyService.getSubReplies(replyId);

        return ResponseEntity
                .ok()
                .body(subReplies);
    }

    // 대댓글 생성 요청
    @PostMapping("/sub")
    public ResponseEntity<?> saveSubReply(@RequestBody @Valid SubReplyWriteDto dto,
                                          HttpSession session) {

        log.info("SubReplyWriteDto: {}", dto);

        // flag를 통하여 에러 검사
        boolean flag = replyService.saveSubReply(dto, session);

        if (flag) {
            return ResponseEntity
                    .ok()
                    .body(replyService.getReplies(dto.getReplyId(), new Page(1, 10)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("대댓글 생성에 실패했습니다.");
        }
    }

    // 댓글 수정 요청
    @RequestMapping(value = "/sub", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> updateSubReply(@RequestBody @Valid SubReplyUpdateDto dto) {

        log.info("ReplyUpdateDto: {}", dto);

        boolean flag = replyService.updateSubReply(dto);

        if (flag) {
            return ResponseEntity
                    .ok()
                    .body(replyService.getReplies(dto.getBoardId(), new Page(1, 10)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("대댓글 업데이트에 실패했습니다.");
        }

    }

    // 대댓글 삭제 요청
    @DeleteMapping("/sub")
    public ResponseEntity<?> deleteSubReply(@RequestBody SubReplyDeleteDto dto,
                                            HttpSession session) {

        log.info("SubReplyDeleteDto: {}", dto);

        boolean flag = replyService.deleteSubReply(dto,session);

        if (flag) {
            return ResponseEntity
                    .ok()
                    .body(replyService.getReplies(dto.getBoardId(), new Page(1, 10)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("대댓글 삭제에 실패했습니다.");
        }

    }
}
