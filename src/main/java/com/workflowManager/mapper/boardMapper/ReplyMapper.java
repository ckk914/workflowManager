package com.workflowManager.mapper.boardmapper;

import com.workflowManager.common.boardpage.Page;
import com.workflowManager.entity.board.Reply;
import com.workflowManager.entity.board.SubReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 댓글 목록 조회
    List<Reply> replies(@Param("boardId") int boardId, @Param("p") Page page);

    // 댓글 상세 조회
    Reply findOne(int replyId);

    // 댓글 등록
    boolean save(Reply reply);

    // 댓글 수정
    boolean update(Reply reply);

    // 댓글 삭제
    boolean delete(int replyId);

    // 게시물 당 총 댓글 수 조회
    int countReply(int boardId);

    // 대댓글 목록 조회
    List<SubReply> subReplies(int replyId);

    // 대댓글 등록
    boolean saveSubReply(SubReply subReply);

    // 대댓글 상세 조회
    SubReply findOneSubReply(int subReplyId);

    // 대댓글 수정
    boolean updateSubReply(SubReply subReply);

    // 대댓글 삭제
    boolean deleteSubReply(int subReplyId);
}
