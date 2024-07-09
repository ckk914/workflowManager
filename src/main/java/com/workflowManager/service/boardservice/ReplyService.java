package com.workflowManager.service.boardservice;

import com.workflowManager.common.boardpage.Page;
import com.workflowManager.common.boardpage.PageMaker;
import com.workflowManager.dto.replydto.*;
import com.workflowManager.entity.board.Reply;
import com.workflowManager.entity.board.SubReply;
import com.workflowManager.mapper.boardMapper.BoardMapper;
import com.workflowManager.mapper.boardmapper.ReplyMapper;
import com.workflowManager.util.LoginUtil;
import com.workflowManager.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private BoardMapper boardMapper;

    // 댓글 목록 전체조회
    public ReplyListDto getReplies(int boardId,
                                   Page page
    ) {

        List<Reply> replies = replyMapper.replies(boardId, page);

        List<ReplyDetailDto> dtoList = replies.stream()
                .map(r -> new ReplyDetailDto(r))
                .collect(Collectors.toList());

        return ReplyListDto.builder()
                .replies(dtoList)
                .pageInfo(new PageMaker(page, replyMapper.countReply(boardId)))
                .build();
    }

    public boolean save(ReplyWriteDto dto, HttpSession session) {

        // 비밀번호 해싱
        String rawPassword = dto.getReplyPassword(); // dto에서 비밀번호를 가져오는 메서드가 있다고 가정
        String hashedPassword = PasswordUtil.hashPassword(rawPassword);

        // Reply 엔티티 생성 및 설정
        Reply reply = dto.toEntity();
        reply.setUserId(LoginUtil.getLoggedInUserAccount(session));
        reply.setReplyPassword(hashedPassword); // 해싱된 비밀번호 설정

        boolean save = replyMapper.save(reply);

        boardMapper.updateCount();

        return save;
    }

    public boolean update(ReplyUpdateDto dto) {

        Reply original = replyMapper.findOne(dto.getReplyId());

        if (PasswordUtil.checkPassword(dto.getEditReplyPassword(), original.getReplyPassword())) {
            Reply modifyReply = dto.toEntity();
            log.info(modifyReply.toString());
            boolean update = replyMapper.update(modifyReply);
            return update;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean delete(ReplyDeleteDto dto,
                          HttpSession session) {

        Reply original = replyMapper.findOne(dto.getReplyId());

        if (LoginUtil.isAdmin(session)) {
            boolean delete = replyMapper.delete(dto.getReplyId());
            boardMapper.updateCount();
            return delete;
        }

        if (PasswordUtil.checkPassword(dto.getReplyDeletePassword(), original.getReplyPassword())) {
            boolean delete = replyMapper.delete(dto.getReplyId());
            boardMapper.updateCount();
            return delete;
        } else {
            return false;
        }

    }

    // 대댓글 목록 전체조회
    public SubReplyListDto getSubReplies(int replyId) {

        List<SubReply> subReplies = replyMapper.subReplies(replyId);

        log.info(subReplies.toString());

        List<SubReplyDetailDto> dtoList = subReplies.stream()
                .map(subReply -> new SubReplyDetailDto(subReply))
                .collect(Collectors.toList());

        return SubReplyListDto.builder()
                .subReplies(dtoList)
                .build();
    }

    public boolean saveSubReply(SubReplyWriteDto dto, HttpSession session) {

        SubReply subReply = dto.toEntity();

        subReply.setUserId(LoginUtil.getLoggedInUserAccount(session));

        subReply.setSubReplyPassword(PasswordUtil.hashPassword(dto.getSubReplyPassword()));

        boolean save = replyMapper.saveSubReply(subReply);

        return save;
    }

    public boolean updateSubReply(SubReplyUpdateDto dto) {

        SubReply original = replyMapper.findOneSubReply(dto.getSubReplyId());

        if (PasswordUtil.checkPassword(dto.getEditSubReplyPassword(), original.getSubReplyPassword())) {

            SubReply modifyReply = dto.toEntity();

            log.info(modifyReply.toString());

            boolean update = replyMapper.updateSubReply(modifyReply);

            return update;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteSubReply(SubReplyDeleteDto dto,
                                  HttpSession session) {

        SubReply original = replyMapper.findOneSubReply(dto.getSubReplyId());

        if(LoginUtil.isAdmin(session)){
            boolean delete = replyMapper.deleteSubReply(dto.getSubReplyId());

            return delete;
        }

        if (PasswordUtil.checkPassword(dto.getSubReplyDeletePassword(), original.getSubReplyPassword())) {

            boolean delete = replyMapper.deleteSubReply(dto.getSubReplyId());

            return delete;
        } else {
            return false;
        }
    }
}
