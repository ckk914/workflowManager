package com.workflowManager.util;

import com.workflowManager.dto.user.LoginUserInfoDto;
import com.workflowManager.entity.AccessLevel;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {
    public static final String LOGIN = "login";
    public static final String AUTO_LOGIN_COOKIE = "auto";

    // 로그인 여부 확인
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(LOGIN) != null;
    }

  //로그인한 회원의 계정명 얻기
    public static String getLoggedInUserAccount(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        return loggedInUser != null ? loggedInUser.getUserId() : null;
    }

    //♦︎ - 신윤종 추가
    // 세션에서 department 가져오기
    //로그인한 회원의 부서명 얻기
    public static String getLoggedInDepartmentId(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        return loggedInUser != null ? loggedInUser.getDepartmentId() : null;
    }

    public static LoginUserInfoDto getLoggedInUser(HttpSession session) {
        return (LoginUserInfoDto) session.getAttribute(LOGIN);
    }

    public static boolean isAdmin(HttpSession session) {
        //로그인한 사람 정보 가져옴
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        AccessLevel auth = null;
        if (isLoggedIn(session)) {   //로그인 했으면
            auth = AccessLevel.valueOf(loggedInUser.getUserAccessLevel());  //권한 정보 가져옴
        }
        return auth == AccessLevel.ADMIN;  //관리자인지 체크
    }
    public static boolean isMine(String boardAccount, String loggedInUserAccount) {
        return boardAccount.equals(loggedInUserAccount);
    }
    //자동 로그인 검증
    public static boolean isAutoLogin(HttpServletRequest request) {
        Cookie autoLoginCookie = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);
        return autoLoginCookie != null;   // 널이 아니면 자동로그인 상태
    }

}
