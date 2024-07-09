package com.workflowManager.dto.replydto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SubReplyDeleteDto {

    private int boardId;
    private int subReplyId;
    private String subReplyDeletePassword;

}
