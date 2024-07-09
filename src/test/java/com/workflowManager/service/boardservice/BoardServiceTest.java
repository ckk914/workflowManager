package com.workflowManager.service.boardservice;

import com.workflowManager.dto.boarddto.BoardWriteDto;
import com.workflowManager.entity.board.Board;
import com.workflowManager.mapper.boardMapper.BoardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardMapper boardMapper;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }

    @Test
    @DisplayName("저장을 누르면 게시물이 저장된다.")
    void save() {

        //given
        int numBoards = 100;
        //when
        for (int i = 0; i < numBoards; i++) {
            BoardWriteDto dto = new BoardWriteDto();
            dto.setBoardTitle("Test Title " + i);
            dto.setBoardNickname("John Doe");
            dto.setBoardContent("Test Content " + i);
            dto.setBoardPassword("1234" + i);
            Board b = dto.toEntity();
            b.setBoardCreatedAt(LocalDateTime.now());
            boolean save = boardMapper.save(b);// 게시물 저장 메서드 호출
            assertTrue(save); // 각 저장 결과가 true인지 검증
        }
        //then
//        verify(boardMapper, times(numBoards)).save(any(Board.class)); // save 메서드가 총 numBoards 번 호출되었는지 검증
    }

}