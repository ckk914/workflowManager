package com.workflowManager.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String employeeId;
    private String userId;
    @Setter
    private String password;
    private String userName;
    private String userPosition;
    private String userEmail;
    private AccessLevel userAccessLevel;

    private String departmentId;
    private String sessionId;
    private LocalDateTime limitTime;

    @Setter
    private String profileImg;
    @Setter
    private String userCreatedAt;
    private String departmentName;
}

