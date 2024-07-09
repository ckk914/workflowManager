package com.workflowManager.dto.lunchBoardDto;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder @ToString
public class LunchBoardFindAllDto {
    private int lunchPostNumber; // 게시글 번호
    private String userId; // 유저 아이디
    private String lunchPostTitle; // 글 제목
    private LocalDateTime lunchDate; // 작성일시
    private String eatTime; // 식사 일정
    private String lunchLocation; // 식당 위치 (식당명)
    private String lunchMenuName; // 메뉴 이름
    private int lunchAttendees; // 최대 모집인원 수
    private int progressStatus; // 모집 인원 진행 상황
}
