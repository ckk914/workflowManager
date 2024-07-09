package com.workflowManager.entity.schedule;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TeamTodoList {
    private int teamTodoId;
    private String teamTodoContent;
    private String teamTodoStatus;
    private LocalDateTime teamTodoCreateAt;
    private LocalDateTime teamTodoUpdateAt;
    private Integer colorIndexId; // Integer로 선언하여 null 값을 허용
    private String userId;
    private String departmentId;

    public TeamTodoList(int teamTodoId, String teamTodoContent, String teamTodoStatus, LocalDateTime teamTodoCreateAt, LocalDateTime teamTodoUpdateAt, Integer colorIndexId, String userId, String departmentId) {
        this.teamTodoId = teamTodoId;
        this.teamTodoContent = teamTodoContent;
        this.teamTodoStatus = teamTodoStatus;
        this.teamTodoCreateAt = teamTodoCreateAt;
        this.teamTodoUpdateAt = teamTodoUpdateAt;
        this.colorIndexId = colorIndexId;
        this.userId = userId;
        this.departmentId = departmentId;
    }

    public int getTeamTodoId() {
        return teamTodoId;
    }

    public void setTeamTodoId(int teamTodoId) {
        this.teamTodoId = teamTodoId;
    }

    public String getTeamTodoContent() {
        return teamTodoContent;
    }

    public void setTeamTodoContent(String teamTodoContent) {
        this.teamTodoContent = teamTodoContent;
    }

    public String getTeamTodoStatus() {
        return teamTodoStatus;
    }

    public void setTeamTodoStatus(String teamTodoStatus) {
        this.teamTodoStatus = teamTodoStatus;
    }

    public LocalDateTime getTeamTodoCreateAt() {
        return teamTodoCreateAt;
    }

    public void setTeamTodoCreateAt(LocalDateTime teamTodoCreateAt) {
        this.teamTodoCreateAt = teamTodoCreateAt;
    }

    public LocalDateTime getTeamTodoUpdateAt() {
        return teamTodoUpdateAt;
    }

    public void setTeamTodoUpdateAt(LocalDateTime teamTodoUpdateAt) {
        this.teamTodoUpdateAt = teamTodoUpdateAt;
    }

    public Integer getColorIndexId() {
        return colorIndexId;
    }

    public void setColorIndexId(Integer colorIndexId) {
        this.colorIndexId = colorIndexId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}

