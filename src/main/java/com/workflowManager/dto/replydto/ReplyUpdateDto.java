package com.workflowManager.dto.replydto;

import com.workflowManager.entity.board.Reply;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyUpdateDto {

    private int replyId;
    private int boardId;
    @NotNull(message = "댓글 비밀번호는 필수 항목입니다.")
    private String editReplyContent;
    @NotNull(message = "댓글 내용은 필수 항목입니다.")
    private String editReplyPassword;
    private LocalDateTime replyUpdatedAt = LocalDateTime.now();

    public Reply toEntity() {
        return Reply.builder()
                .boardId(this.boardId)
                .replyId(this.replyId)
                .replyContent(this.editReplyContent)
                .replyUpdatedAt(this.getReplyUpdatedAt())
                .build();
    }

}
