package com.workflowManager.entity.schedule;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
@Builder
public class TeamCalendar {
    private int teamCalendarId;
    private String teamCalendarName;
    private LocalDateTime teamCalCreatedAt;
    private String departmentId;


}
