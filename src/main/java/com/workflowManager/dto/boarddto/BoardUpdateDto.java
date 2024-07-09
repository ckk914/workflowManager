package com.workflowManager.dto.boarddto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class BoardUpdateDto {

    private int boardId;
    private String newContent;
    private LocalDateTime boardUpdatedAt = LocalDateTime.now();

}
