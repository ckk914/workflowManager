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
public class Reply {

    private int replyId;
    private int boardId;
    private String userId;
    private String nickName;
    private String replyContent;
    private String replyPassword;
    private LocalDateTime replyCreatedAt = LocalDateTime.now();
    private LocalDateTime replyUpdatedAt;

}
