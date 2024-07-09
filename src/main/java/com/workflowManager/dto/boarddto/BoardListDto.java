package com.workflowManager.dto.boarddto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class BoardListDto {

    private int boardId;
    private String boardTitle;
    private String boardContent;
    private String userId;
    private String boardNickname;
    private LocalDateTime boardCreatedAt;
    private int replyCount;
    private int viewCount;
    private int likes;
    private int dislikes;

    // 날짜 변환 메서드 추가
    public String getFormattedBoardCreatedAt() {
        if (boardCreatedAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return boardCreatedAt.format(formatter);
        } else {
            return "";
        }
    }

}
