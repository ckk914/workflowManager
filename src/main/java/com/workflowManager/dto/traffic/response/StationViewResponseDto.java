package com.workflowManager.dto.traffic.response;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter
public class StationViewResponseDto {

    private int trafficId;
    private String userId;
    private String departure;
    private String arrival;
    private int viewCount;
}
