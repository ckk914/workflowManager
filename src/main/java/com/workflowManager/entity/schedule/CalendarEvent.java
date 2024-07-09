package com.workflowManager.entity.schedule;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class CalendarEvent {
    private int calEventId;
    private LocalDateTime calEventDate;
    private String calEventTitle;
    private String calEventDescription;
    private LocalDateTime calECreateAt;
    private LocalDateTime calEUpdateAt;
    private Integer colorIndexId; // Integer로 선언하여 null 값을 허용
    private String userId;
    private String departmentId;
    private Integer calendarId; // Integer로 선언하여 null 값을 허용
    private Integer teamCalendarId; // Integer로 선언하여 null 값을 허용

}
