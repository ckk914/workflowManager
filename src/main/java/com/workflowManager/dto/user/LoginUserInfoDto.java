package com.workflowManager.dto.user;

import com.workflowManager.entity.User;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInfoDto {

    //클라이언트에 보낼 정보
    private String userId;     //계정명
    private String nickName;    //이름
    private String email;           //이메일
    private String userAccessLevel;            //권한
    private String profile;        //프로필 경로
    private String departmentId;

    //생성자
    public LoginUserInfoDto(User user){
        this.userId = user.getUserId();
        this.nickName = user.getUserName();
        this.email = user.getUserEmail();
        this.userAccessLevel = user.getUserAccessLevel().name();  //.name() 쓰면 enum에서 대문자만 뜯어온다~!
        this.profile=user.getProfileImg();
        this.departmentId = user.getDepartmentId(); //♦︎ - 신윤종 user 단어 추가. ㅠ
    }
}