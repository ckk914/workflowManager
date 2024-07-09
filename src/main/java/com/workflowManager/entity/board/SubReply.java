package com.workflowManager.entity.board;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SubReply {

    private int subReplyId;

    private int replyId;

    private String userId;

    private String nickName;

    private String subReplyContent;

    private String subReplyPassword;

    private LocalDateTime subReplyCreatedAt=LocalDateTime.now();

    private LocalDateTime subReplyUpdatedAt;
}
