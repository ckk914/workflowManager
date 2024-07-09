package com.workflowManager.interceptor;

import com.workflowManager.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration   //스프링이 주입할 수 있도록 (=Component)
@Slf4j
public class ReplyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.debug("after login interceptor execute!");
        if (!LoginUtil.isLoggedIn(request.getSession())) {
            log.info("로그인이 필요합니다.:{}", requestURI);
            response.sendError(403);
            return false;   //true일 경우, 컨트롤러 진입 허용 / false는 진입 차단
        }
        return true;
    }
}
