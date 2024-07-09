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
public class AllMyTeamTodoListDto {
    private int teamTodoId;
    private String teamTodoContent;
    private String teamTodoStatus;
    private LocalDateTime teamTodoCreateAt;
    private LocalDateTime teamTodoUpdateAt;
    private Integer colorIndexId; // Integer로 선언하여 null 값을 허용
    private String userId;
    private String departmentId;
}
