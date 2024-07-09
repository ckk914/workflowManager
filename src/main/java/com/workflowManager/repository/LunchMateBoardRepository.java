package com.workflowManager.repository;

import com.workflowManager.dto.lunchBoardDto.LunchMemberDto;
import com.workflowManager.entity.LunchMateBoard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LunchMateBoardRepository {

    // 게시물 목록 조회
    List<LunchMateBoard> findAll();
    //게시물 가져오기
    LunchMateBoard findOne(int lunchPostNumber);
    // 게시물 상세 조회
    // LunchMateBoard findOne(int lunchPostNumber);

    // 게시물 등록
    boolean save(LunchMateBoard lunchMateBoard);

    // 게시물 삭제
    boolean delete(int lunchPostNumber);


    LunchMateBoard findByLunchPostNumber(int postId);

    LunchMemberDto incrementProgressStatus(int postId);
}
