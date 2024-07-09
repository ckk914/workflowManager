package com.workflowManager.dto.lunchBoardDto;

import com.workflowManager.entity.LunchMateBoard;
import lombok.*;

// dto의 필드명은 반드시 html form태그의 입력태그들 name속성과 일치해야 함.
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LunchBoardWriteRequestDto {

    private String userId; //유저 아이디
    private String lunchPostTitle; // 글 제목
    private String eatTime; // 식사 일정
    private String lunchLocation; // 식당 위치 (식당명)
    private String lunchMenuName; // 메뉴 이름
    private int lunchAttendees; // 최대 모집인원 수

    public LunchMateBoard toEntity() {
        LunchMateBoard lmb = new LunchMateBoard();
        lmb.setUserId(this.userId);
        lmb.setLunchPostTitle(this.lunchPostTitle);
        lmb.setEatTime(this.eatTime);
        lmb.setLunchLocation(this.lunchLocation);
        lmb.setLunchMenuName(this.lunchMenuName);
        lmb.setLunchAttendees(this.lunchAttendees);

        return lmb;
    }

}
