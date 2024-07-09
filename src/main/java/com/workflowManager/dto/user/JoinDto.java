package com.workflowManager.dto.user;

import com.workflowManager.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//회원가입할때 사용할 객체
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JoinDto {
    @NotBlank(message = "아이디는 필수값입니다.")
    @Size(min=4,max=14, message="아이디는 4~14글자")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문과 숫자만 포함해야 합니다.")
    private String userId;

    @NotBlank
    private String password;
    @NotBlank
    @Size(min=2, max=6)
    private String userName;

    @NotBlank
    @Email
    private String userEmail;
    //프로필 사진 데이터 // 여러장이면 List로 읽기
    private MultipartFile profileImg;
    //사번
    private String employeeId;
    //직책
    private String userPosition;
    //가입 날짜
//    private String userCreateAt;

//    private String accessLevel;
    //부서 id
    private String departmentId;

    public User toEntity() {
//        new Member(
//        this.account,
//        this.password,
//        this.email,
//        this.name
//                  ll
        return User.builder()
                .employeeId(this.employeeId)
                .password(this.password)
                .userEmail(this.userEmail)
                .userName(this.userName)
                .userId(this.userId)
                .userPosition(this.userPosition)
                .departmentId(this.departmentId)
                .build();
    }
}
