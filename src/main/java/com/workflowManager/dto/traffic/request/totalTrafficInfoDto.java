package com.workflowManager.dto.traffic.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class totalTrafficInfoDto {

    private String userId;
    private String arrival;
    private String departure;

    private LocalDateTime regDateTime;

}
