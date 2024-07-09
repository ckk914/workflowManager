package com.workflowManager.dto.replydto;

import com.workflowManager.entity.board.Reply;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyWriteDto {

    // 컨트롤러의 @Validated와 함께 유효성 검사 추후 추가
    private int boardId;

    private String userId;

    @NotNull(message = "닉네임은 필수 항목입니다.")
    private String nickName;

    @NotNull(message = "댓글 내용은 필수 항목입니다.")
    private String replyContent;

    @NotNull(message = "댓글 비밀번호는 필수 항목입니다.")
    private String replyPassword;

    public Reply toEntity() {
        return Reply.builder()
                .boardId(this.boardId)
                .userId(this.userId)
                .replyContent(this.replyContent)
                .replyPassword(this.replyPassword)
                .nickName(this.nickName)
                .build();
    }
}
