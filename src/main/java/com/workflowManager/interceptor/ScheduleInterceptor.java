package com.workflowManager.interceptor;

import com.workflowManager.mapper.scheduleMapper.CalendarMapper;
import com.workflowManager.mapper.scheduleMapper.TodoListMapper;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//삭제 본인만 할 수 있도록 컨피그 진행
@Configuration   //스프링이 주입할 수 있도록 (=Component)
@Slf4j
@RequiredArgsConstructor
public class ScheduleInterceptor implements HandlerInterceptor {

    private final TodoListMapper todoListMapper;
    private final CalendarMapper calendarMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("You are trying to access someone else's schedule!!");
        if(!LoginUtil.isLoggedIn(request.getSession())) {
            log.info("로그인이 필요합니다.:{}", request.getRequestURI());

            // JavaScript를 통해 알림창을 띄우고 리다이렉트
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('로그인이 필요합니다!'); location.href='/login';</script>");
            response.getWriter().flush();

            return false;
        }
        return true;
    }
}

    /*
        // 수정과 삭제 권한 config
        //현재 세션이 있는지 조회
        HttpSession session = request.getSession();

        //유저 아이디 조회
        String loggedInUserAccount = getLoggedInUserAccount(session);
        //로그인 한 유저 조회
        String loggedInUserDeptId = getLoggedInDepartmentId(session);

        //세션과 각 매퍼에서 주입하려 할 때 세션이 없으면 로그인 창 리다이렉션
        int calEventId = Integer.parseInt(request.getParameter("calEventId"));
        int todoId
        //세션과 각 매퍼에서 사용자가 같지 않으면 access-denyed 진행
        CalendarEvent calendarEvent = calendarMapper.findOneMyCalEvent(calEventId);
        TeamTodoList teamTodoList = todoListMapper.findByTodoId("todoId");



        int bno = Integer.parseInt(request.getParameter("bno"));
        //접근당하는 보드의 넘버
        Board board = boardMapper.findOne(bno);
        //접근당하는 보드의 어카운트
        String boardAccount = board.getAccount();
        //접근당하는 보드 어카운트 = 현재 세션 로그인
        if((loggedInUserAccount.equals(boardAccount)) && (LoginUtil.isLoggedIn(session)) ) {
            //보드 글번호 조회해서 새로운 쿠키 넣기

            return true; //회원이고 남이 쓴 글일때 한시간에 1회씩 조회수 올린다.
        }
        return false;//비회원이거나 자기가 쓴 글일 때 카운트를 하지 않는다.
    }

     */



