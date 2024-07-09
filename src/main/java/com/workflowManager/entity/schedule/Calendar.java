package com.workflowManager.entity.schedule;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
@Builder
public class Calendar {
    private int calendarId;
    private String calendarName;
    private LocalDateTime calCreatedAt;
    private String userId;
}
