package com.workflowManager.entity.user;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    private String departmentName;
    private String departmentId;
}
