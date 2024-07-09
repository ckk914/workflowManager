package com.workflowManager.dto.replydto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyDeleteDto {

    private int boardId;
    private int replyId;
    private String replyDeletePassword;

}
