package com.workflowManager.dto.replydto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.workflowManager.entity.board.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyDetailDto {

    private int replyId;
    private String replyContent;
    private String nickName;
    private String userId;
    private LocalDateTime replyCreatedAt;
    private LocalDateTime replyUpdatedAt;
//    private String profileImg;

    // 엔터티를 DTO로 변환하는 생성자
    public ReplyDetailDto(Reply r) {
        this.replyId = r.getReplyId();
        this.userId = r.getUserId();
        this.replyContent = r.getReplyContent();
        this.nickName = r.getNickName();
        this.replyCreatedAt = r.getReplyCreatedAt();
        this.replyUpdatedAt = r.getReplyUpdatedAt();
//        this.profileImg = f.getProfileImg();
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
        } else {
            return "";
        }
    }

    @JsonProperty("replyCreatedAt")
    // 작성일 포맷 메서드
    public String getFormattedReplyCreatedAt() {
        return formatDate(replyCreatedAt);
    }

    @JsonProperty("replyUpdatedAt")
    // 수정일 포맷 메서드
    public String getFormattedReplyUpdatedAt() {
        return formatDate(replyUpdatedAt);
    }

}
