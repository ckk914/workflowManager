package com.workflowManager.interceptor;

import com.workflowManager.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration   //스프링이 주입할 수 있도록 (=Component)
@Slf4j
public class BoardInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("after login interceptor execute!");
        if (!LoginUtil.isLoggedIn(request.getSession())) {

            String requestURI = request.getRequestURI();

            // 게시물 조회를 누를때 주소값을 저장해서 목록으로 돌아갈때 다시 리다이렉트
            String redirect = request.getHeader("Referer");
            if (redirect != null && redirect.contains("/detail")) {
                request.getSession().setAttribute("redirect", redirect);
            }

            log.info("로그인이 필요합니다.:{}", requestURI);
            response.sendRedirect("/login");  //리다이렉트로 로그인 페이지로 보냄
            return false;   //true일 경우, 컨트롤러 진입 허용 / false는 진입 차단
        }
        return true;
    }

}
