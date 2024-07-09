package com.workflowManager.entity.trrafic;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class StationView {

    private String userId;
    private String arrival;
    private String departure;
    private String station;
    private String needTime;
    private LocalDateTime regDateTime;

}
