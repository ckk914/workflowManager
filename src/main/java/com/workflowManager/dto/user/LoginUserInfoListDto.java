package com.workflowManager.dto.user;

import com.workflowManager.entity.User;
import lombok.*;


//♦︎ - 신윤종 추가
// 투두리스트 및 달력 조회를 위한 세션 디티오 생성

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInfoListDto {
    //클라이언트에 보낼 정보
    private String userId;     //계정명
    private String nickName;    //이름
    private String departmentId; // 부서 아이디
    private String profile;        //프로필 경로
    public LoginUserInfoListDto(User user){
        this.userId = user.getUserId();
        this.nickName = user.getUserName();
        this.profile=user.getProfileImg();
        this.departmentId = getDepartmentId();
    }

}
