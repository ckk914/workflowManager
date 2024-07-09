package com.workflowManager.mapper.boardMapper;

import com.workflowManager.common.boardpage.Search;
import com.workflowManager.entity.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시물 목록 조회
    List<Board> findAll(Search page);

    // 게시물 상세 조회
    Board findOne(long boardId);

    // 게시물 등록
    boolean save(Board board);

    // 게시물 삭제
    boolean delete(int boardId);

    // 조회수 상승
    boolean updateViewCount(int boardId);

    // 게시물 수정
    boolean update(Board board);

    // 총 게시물 수 조회
    int count(Search page);

    // 댓글 수 업데이트
    boolean updateCount();

    // 좋아요 수 올리기
    boolean upLikeCount(int boardId);

    // 좋아요 수 내리기
    boolean downLikeCount(int boardId);

    // 싫어요 수 올리기
    boolean upDislikeCount(int boardId);

    // 싫어요 수 내리기
    boolean downDislikeCount(int boardId);
}
