package com.workflowManager.dto.replydto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.workflowManager.entity.board.SubReply;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class SubReplyDetailDto {

    private int replyId;
    private int subReplyId;
    private String subReplyContent;
    private String nickName;
    private String userId;
    private LocalDateTime subReplyCreatedAt;
    private LocalDateTime subReplyUpdatedAt;
//    private String profileImg;

    // 엔터티를 DTO로 변환하는 생성자
    public SubReplyDetailDto(SubReply s) {
        this.replyId = s.getReplyId();
        this.subReplyId = s.getSubReplyId();
        this.userId = s.getUserId();
        this.subReplyContent = s.getSubReplyContent();
        this.nickName = s.getNickName();
        this.subReplyCreatedAt = s.getSubReplyCreatedAt();
        this.subReplyUpdatedAt = s.getSubReplyUpdatedAt();
//        this.profileImg = s.getProfileImg();
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
        } else {
            return "";
        }
    }

    @JsonProperty("subReplyCreatedAt")
    // 작성일 포맷 메서드
    public String getFormattedSubReplyCreatedAt() {
        return formatDate(subReplyCreatedAt);
    }

    @JsonProperty("subReplyUpdatedAt")
    // 수정일 포맷 메서드
    public String getFormattedSubReplyUpdatedAt() {
        return formatDate(subReplyUpdatedAt);
    }
}
