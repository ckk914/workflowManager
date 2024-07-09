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
public class Board {

    private int boardId;
    private String userId;
    private String boardNickname;
    private String boardTitle;
    private String boardContent;
    private String boardPassword;
    private LocalDateTime boardCreatedAt = LocalDateTime.now();
    private LocalDateTime boardUpdatedAt;
    private int replyCount;
    private int viewCount;
    private int likes;
    private int dislikes;

}
