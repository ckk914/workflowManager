package com.workflowManager.dto.schedule_dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class CalendarsDto {
    private int calendarId;
    private String calendarName;
    private int teamCalendarId;
    private String teamCalendarName;
    private String userId;
    private String userName;
}
