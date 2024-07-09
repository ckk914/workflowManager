package com.workflowManager.dto.boarddto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDetailDto {

    private int boardId;
    private String userId;
    private String boardTitle;
    private String boardNickname;
    private String boardContent;
    private String boardPassword;
    private int viewCount;
    private int likes;
    private int dislikes;
    private LocalDateTime boardCreatedAt;
    private LocalDateTime boardUpdatedAt;

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            return dateTime.format(formatter);
        } else {
            return "";
        }
    }

    // 작성일 포맷 메서드
    public String getFormattedBoardCreatedAt() {
        return formatDate(boardCreatedAt);
    }

    // 수정일 포맷 메서드
    public String getFormattedBoardUpdateAt() {
        return formatDate(boardUpdatedAt);
    }
}
