package com.workflowManager.service.replyservice;

import com.workflowManager.dto.replydto.ReplyWriteDto;
import com.workflowManager.entity.board.Reply;
import com.workflowManager.mapper.boardMapper.BoardMapper;
import com.workflowManager.mapper.boardmapper.ReplyMapper;
import com.workflowManager.service.boardservice.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private ReplyService replyService;

    @Test
    void save100Replies(HttpSession session) {
        for (int i = 1; i <= 100; i++) {
            // Given
            ReplyWriteDto dto = new ReplyWriteDto();
            dto.setReplyContent("댓글 내용 " + i);
            dto.setNickName("닉네임 " + i);
            dto.setBoardId(104); // 예시로 보드 아이디를 1로 설정
            dto.setReplyPassword("password" + i);

            Reply reply = dto.toEntity();

//            when(replyMapper.save(reply)).thenReturn(true);

            // When
            boolean result = replyService.save(dto, session);

            // Then
            assertTrue(result);
        }

    }
}
