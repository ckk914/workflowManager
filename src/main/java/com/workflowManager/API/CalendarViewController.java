package com.workflowManager.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflowManager.dto.schedule_dto.request.AllMyCalendarEventDto;
import com.workflowManager.dto.schedule_dto.request.AllMyTeamCalendarEventDto;
import com.workflowManager.service.UserService;
import com.workflowManager.service.schedule.CalendarService;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 주 기능 : 세션에 저장된 정보를 JSP에 전달
 *
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/myCalendar")
public class CalendarViewController {

    private final UserService userService;
    private final CalendarService calendarService;
    private final ObjectMapper objectMapper;

    //======================= 개인 메서드 ==========================

    //개인 일정 달별로 조회하기
    @GetMapping("/viewMyEvent")
    public String viewCalendar(Model model, HttpSession session) {
        // 세션에서 userId 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            log.info("userId 가 없습니다. {}", session.getAttribute("userId"));
            throw new RuntimeException("User is not logged in");
        }
        try {
            List<AllMyCalendarEventDto> myCalendarEvents = calendarService.getMyAllEvents(userId);

            String myCalEventsJson = objectMapper.writeValueAsString(myCalendarEvents);
            model.addAttribute("myCalEvents", myCalEventsJson.replace("'", "\\'"));
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            model.addAttribute("formattedDate", formattedDate);

            String userName = LoginUtil.getLoggedInUser(session).getNickName();

            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);

        } catch (Exception e) {
            log.error("Error converting events to JSON", e);
        }
        return "schedule/myCalendar";
    }

    //개인 일정 추가 화면처리
    @PostMapping("/addMyEvent")
    public String addMyEvent(@ModelAttribute AllMyCalendarEventDto myCalendarEventDto, HttpSession session) {
        // 세션에서 userId userName 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String userName = LoginUtil.getLoggedInUser(session).getNickName();
        if (userId == null || userName == null) {
            throw new RuntimeException("User is not logged in");
        }
        calendarService.addEvent(myCalendarEventDto, userId, userName);
        return "redirect:/calendar/viewMyEvent/" + userId;
    }

    //개인 일정 수정 화면처리
    @PostMapping("/updateMyEvent")
    public String editMyEvent(@ModelAttribute AllMyCalendarEventDto myCalendarEventDto, HttpSession session) {
        // 세션에서 userId 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        // 이벤트 수정 호출
        boolean success = calendarService.updateCalEvent(myCalendarEventDto);
        if (!success) {
            throw new RuntimeException("Failed to update event");
        }
        return "redirect:/calendar/viewMyEvent/" + userId;
    }

    //일정 삭제 화면처리
    @GetMapping("/deleteMyEvent/{calEventId}")
    public String deleteCalEvent(@PathVariable int calEventId, HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        boolean success = calendarService.deleteCalEvent(calEventId);
        if(!success) {
            throw new RuntimeException("failed to delete event");
        }
        return "redirect:/calendar/viewMyEvent/" + userId;
    }

    //======================= 팀 메서드 ==========================

    //팀 일정 달별로 조회하기
    @GetMapping("/viewTeamEvent")
    public String viewTeamCalendar(Model model, HttpSession session) {
        // 세션에서 userId 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);
        Integer teamCalendarId = calendarService.getTeamId(departmentId);
        String departmentName = userService.findOneDepartmentName(departmentId);
        if (userId == null) {
            // 인터셉터가 처리하지 못한 경우를 대비한 예외 처리
            return "redirect:/login";
        }
        try {
            List<AllMyTeamCalendarEventDto> teamCalEvents = calendarService.getAllTeamEvents(departmentId);

            // model에 필요한 데이터 추가
            model.addAttribute("departmentId", departmentId);
            model.addAttribute("teamCalendarId", teamCalendarId);
            model.addAttribute("teamCalEvents", teamCalEvents);
            model.addAttribute("departmentName", departmentName);

            // formattedDate 설정
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            model.addAttribute("formattedDate", formattedDate);

            //현재 유저 이름 설정 : 수정을 위한 세션값
            String userName = LoginUtil.getLoggedInUser(session).getNickName();
            model.addAttribute("userName", userName);


        } catch (Exception e) {
            log.error("Error converting events to JSON", e);
        }
        return "schedule/teamCalendar";
    }

    //일정 추가 화면처리
    @PostMapping("/addTeamEvent")
    public String addTeamEvent(@ModelAttribute AllMyTeamCalendarEventDto teamCalendarEventDto, HttpSession session) {
        // 세션에서 userId userName 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);
        String userName = LoginUtil.getLoggedInUser(session).getNickName();
        Integer teamCalendarId = calendarService.getTeamId(departmentId);
        if (userId == null || userName == null) {
            throw new RuntimeException("User is not logged in");
        }
        calendarService.addTeamEvent(teamCalendarEventDto, userId, userName, departmentId);
        return "redirect:/calendar/viewTeamEvent/" + departmentId;
    }

    // 일정 수정 화면처리
    @PostMapping("/updateTeamEvent")
    public String editTeamEvent(@ModelAttribute AllMyTeamCalendarEventDto teamCalendarEventDto, HttpSession session) {
        // 세션에서 userId 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);
        String userName = LoginUtil.getLoggedInUser(session).getNickName();
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        // 이벤트 수정 호출
        boolean success = calendarService.updateTeamCalEvent(teamCalendarEventDto);
        if (!success) {
            throw new RuntimeException("Failed to update event");
        }
        return "redirect:/calendar/viewTeamEvent/" + departmentId;
    }

    //일정 삭제 화면처리
    @GetMapping("/deleteTeamEvent/{calEventId}")
    public String deleteTeamCalEvent(@PathVariable int calEventId, HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        boolean success = calendarService.deleteCalEvent(calEventId);
        if(!success) {
            throw new RuntimeException("failed to delete event");
        }
        return "redirect:/calendar/viewTeamEvent/" + departmentId;
    }


}

