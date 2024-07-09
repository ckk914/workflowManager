package com.workflowManager.service;

import com.workflowManager.dto.DepartmentNameDto;
import com.workflowManager.dto.user.*;
import com.workflowManager.entity.User;
import com.workflowManager.mapper.userMapper.UserMapper;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.workflowManager.service.LoginResult.*;
import static com.workflowManager.util.LoginUtil.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper usermapper;
    private final PasswordEncoder encoder;

    // 부서명 전체 리턴
    public List<DepartmentNameDto> findDepartmentName() {
        List<DepartmentNameDto> dList = usermapper.findDepartmentAll();
        return dList;
    }

    public boolean join(JoinDto dto, String profilePath) {
        User user = dto.toEntity();
        user.setProfileImg(profilePath); // 프로필 사진 경로 엔터티에 설정
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println(user);
        // 날짜와 시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 형식화된 문자열로 출력
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Current Date and Time: " + formattedDateTime);
        user.setUserCreatedAt(formattedDateTime);

        String encodedPassword = encoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        //저장
        return usermapper.save(user);
    }  //end join

    // 로그인 검증 처리
    public LoginResult authenticate(LoginDto dto, HttpSession session,
                                    HttpServletResponse response) {

        // 회원가입 여부 확인
        String userId = dto.getUserId();
        //멤버 조회 db
        User foundMember = usermapper.findOne(userId);
        log.debug("멤버 조회 found 확인:{}", foundMember);

        if (foundMember == null) {
            log.info("{} - 회원가입이 필요합니다.", userId);
            return NO_ACC;
        }

        // 비밀번호 일치 검사
        String inputPassword = dto.getPassword(); // 클라이언트에 입력한 비번
        String originPassword = foundMember.getPassword(); // 데이터베이스에 저장된 비번

        // PasswordEncoder에서는 암호화된 비번을 내부적으로 비교해주는 기능을 제공
        if (!encoder.matches(inputPassword, originPassword)) {
            log.info("비밀번호가 일치하지 않습니다.");
            return NO_PW;
        }


        //  ## 자동로그인 추가 처리
        if (dto.isAutoLogin()) {
            // 1. 자동 로그인 쿠키 생성
            // - 쿠키 내부에 절대로 중복되지 않는 유니크한 값을 저장
            //   (UUID, SessionID)
            String sessionId = session.getId();     //브라우저 재시작 하면 변함
            Cookie autoLoginCookie =
                    new Cookie(AUTO_LOGIN_COOKIE, sessionId);
            // 쿠키 설정
            autoLoginCookie.setPath("/"); // 쿠키를 사용할 경로
            autoLoginCookie.setMaxAge(60 * 60 * 24 * 90); // 자동로그인 유지 시간

            // 2. 쿠키를 클라이언트에 전송 - 응답바디에 실어보내야 함
            // response : 응답 객체
            response.addCookie(autoLoginCookie);

            // 3. DB에도 해당 쿠키값을 저장
            usermapper.updateAutoLogin(
                    AutoLoginDto.builder()
                            .sessionId(sessionId)
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .userId(userId)
                            .build()
            );
        }

        maintainLoginState(session, foundMember);

        return SUCCESS;
    }

    public static void maintainLoginState(HttpSession session, User foundUser) {
        log.info("{}님 로그인 성공", foundUser.getUserName());
        log.info("프로필 사진:", foundUser.getProfileImg());
        log.info("부서아이디 : {}", foundUser.getDepartmentId());
        log.info("프로필이미지 : {}", foundUser.getProfileImg());
        // 세션의 수명 : 설정된 시간 OR 브라우저를 닫기 전까지
        int maxInactiveInterval = session.getMaxInactiveInterval();
        session.setMaxInactiveInterval(60 * 60); // 세션 수명 1시간 설정
        log.debug("session time: {}", maxInactiveInterval);
        //세션에 로그인한 회원 정보 세팅⭐️ -> 로그인 핵심 코드
        session.setAttribute(LOGIN, new LoginUserInfoDto(foundUser));
    }


    // 아이디, 이메일 중복검사
    public boolean checkIdentifier(String type, String keyword) {
        return usermapper.existsById(type, keyword);
    }


    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {
        // 1. 쿠키 제거하기
        Cookie c = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);
        if (c != null) {
            c.setPath("/");
            c.setMaxAge(0);  //0초로 하면 제거됨.
            response.addCookie(c);
        }
        //2. db에 자동로그인 컬럼들을 원래대로 돌림
        usermapper.updateAutoLogin(
                AutoLoginDto.builder()
                        .sessionId("none")
                        .limitTime(LocalDateTime.now())
                        .userId(LoginUtil.getLoggedInUserAccount(request.getSession()))
                        .build()
        );
    } //auto LoginClear

    // 아이디 검증 처리
    public User findOneUser(findUserDto dto) {

        // 회원가입 여부 확인
        String userId = dto.getUserId();
        //멤버 조회 db
        User foundMember = usermapper.findOne(userId);
        log.debug("멤버 조회 found 확인:{}", foundMember);

        return  foundMember;
    }

    // ⎏ - 윤종 추가
    public String findOneDepartmentName(String departmentId) {
       return usermapper.findOneDepartmentName(departmentId);
    }

    //비밀번호 변경!
    public boolean updatePassword(UserChangeDto dto) {
        UserChangeDto user = dto.toEntity();

        String password = encoder.encode(dto.getPassword());
        user.setPassword(password);
//        System.out.println("🫡encodedPassword = " + password);
        // 변경 ㄱㄱ
        return usermapper.UserSetPassword(password,dto.getUserId());
//        return true;
    }  //end join

}/* endUserService */
