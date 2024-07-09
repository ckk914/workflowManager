package com.workflowManager.dto.lunchBoardDto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LunchMemberDto {
    private String userId;
    private String lunchPostNumber;
    private String lunchPostTitle;

}
