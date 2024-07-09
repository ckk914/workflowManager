package com.workflowManager.dto.user;

import lombok.*;

@Setter
@Getter
@ToString        //setter 안붙이면 null들어옴
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    private String userId;
    private String password;
    private boolean autoLogin; //자동 로그인 체크 여부
}
