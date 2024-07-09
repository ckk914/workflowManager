package com.workflowManager.mapper;

import com.workflowManager.entity.LunchMateBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LunchMateBoardMapper {

    // 게시물 목록 조회
    List<LunchMateBoard> findAll();
    //게시물 1개 조회
    LunchMateBoard findOne(int lunchPostNumber);

//    // 게시물 상세 조회
//    LunchMateBoard findOne(int lunchPostNumber);

    // 게시물 등록
    boolean save(LunchMateBoard lunchMateBoard);

    // 게시물 삭제
    boolean delete(int lunchPostNumber);

    // 점심게시판 인원 참가 상태
    void incrementProgressStatus(String lunchPostNumber);


    // 페이징 처리된 게시물 목록 조회
    List<LunchMateBoard> findPagedBoards(@Param("offset") int offset, @Param("amount") int amount);

    // 전체 게시물 수 조회
    int getTotalCount();

    // 조회수 상승 (예제에서는 사용되지 않지만 필요 시 추가)
    // void upViewCount(int lunchPostNumber);
}
