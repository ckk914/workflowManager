package com.workflowManager.dto.replydto;

import com.workflowManager.entity.board.SubReply;
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
public class SubReplyUpdateDto {

    private int boardId;
    private int replyId;
    private int subReplyId;
    @NotNull(message = "댓글 비밀번호는 필수 항목입니다.")
    private String editSubReplyContent;
    @NotNull(message = "댓글 내용은 필수 항목입니다.")
    private String editSubReplyPassword;
    private LocalDateTime subReplyUpdatedAt = LocalDateTime.now();

    public SubReply toEntity() {
        return SubReply.builder()
                .replyId(this.replyId)
                .subReplyId(this.subReplyId)
                .subReplyContent(this.editSubReplyContent)
                .subReplyUpdatedAt(this.getSubReplyUpdatedAt())
                .build();
    }

}
