package com.workflowManager.dto.schedule_dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class AllMyCalendarEventDto {
    private int calEventId;
    private String calEventDate; // 서버 클라이언트 시간을 받을 수 있도록 localdate -> instant로 변경
    private String calEventTitle;
    private String calEventDescription;
    private LocalDateTime calEventCreateAt;
    private LocalDateTime calEventUpdateAt;
    private Integer colorIndexId; // Integer로 선언하여 null 값을 허용
    private String userId;
    private Integer calendarId; // Integer로 선언하여 null 값을 허용
    private String userName;

}
