package com.workflowManager.entity.trrafic;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Traffic {

    private String trafficId;
    private String expectedRoute;
    private String expectedTime;
    private String expectedCost;
    private String transportation;
    private String notification;
    private String reservationDispatch;


}
