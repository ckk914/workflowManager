package com.workflowManager.dto.replydto;

import com.workflowManager.entity.board.SubReply;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SubReplyWriteDto {

    // 컨트롤러의 @Validated와 함께 유효성 검사 추후 추가
    private int replyId;

    @NotNull(message = "닉네임은 필수 항목입니다.")
    private String nickName;

    @NotNull(message = "댓글 내용은 필수 항목입니다.")
    private String subReplyContent;

    @NotNull(message = "댓글 비밀번호는 필수 항목입니다.")
    private String subReplyPassword;

    public SubReply toEntity() {
        return SubReply.builder()
                .replyId(this.replyId)
                .subReplyContent(this.subReplyContent)
                .subReplyPassword(this.subReplyPassword)
                .nickName(this.nickName)
                .build();
    }
}
