package com.workflowManager.entity.schedule;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class TodoListEvent {
    private int todoEventId;
    private LocalDateTime todoEventDate;
    private Integer todoId; // Integer로 선언하여 null 값을 허용
    private Integer teamTodoId; // Integer로 선언하여 null 값을 허용
    private Integer colorIndexId; // Integer로 선언하여 null 값을 허용
    private String userId;
    private String departmentId;
}
