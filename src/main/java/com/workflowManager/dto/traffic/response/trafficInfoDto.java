package com.workflowManager.dto.traffic.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter
public class trafficInfoDto {

    private String userId; // 유저아이디
    private String arrival; // 도착지
    private String departure; // 출발지
    private String station; // 역
    private String needTime; // 소요시간
    private LocalDateTime regDateTime; // 현재시간

}