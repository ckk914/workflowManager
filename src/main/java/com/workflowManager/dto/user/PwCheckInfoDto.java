package com.workflowManager.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PwCheckInfoDto {
    private String employeeId;
    private String userEmail;
    private String userName;

}
