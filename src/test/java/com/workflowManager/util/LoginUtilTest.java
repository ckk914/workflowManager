package com.workflowManager.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;

@SpringBootTest
class LoginUtilTest {
    @Test
    public void testGetLoggedInUserInfoList() {
        // 가짜 HttpSession 생성
        HttpSession session = Mockito.mock(HttpSession.class);

        /*
        // 가짜 로그인 사용자 정보 생성
        LoginUserInfoDto fakeLoggedInUser = new LoginUserInfoDto();
        fakeLoggedInUser.setUserId("user123");
        fakeLoggedInUser.setNickName("John Doe");
        fakeLoggedInUser.setDepartmentId("D001");
        fakeLoggedInUser.setProfile("https://example.com/profile");

        // HttpSession.getAttribute() 메서드가 호출될 때 가짜 사용자 정보 반환 설정
        when(session.getAttribute(LoginUtil.LOGIN)).thenReturn(fakeLoggedInUser);

        // 테스트할 메서드 호출
        List<LoginUserInfoListDto> result = LoginUtil.getLoggedInUserInfoList(session);

        // 결과 검증
        assertEquals(1, result.size()); // 리스트에 하나의 요소가 있어야 함
        LoginUserInfoListDto userInfo = result.get(0);
        assertEquals("user123", userInfo.getUserId());
        assertEquals("John Doe", userInfo.getNickName());
        assertEquals("D001", userInfo.getDepartmentId());
        assertEquals("https://example.com/profile", userInfo.getProfile());
    }

         */


    }
}