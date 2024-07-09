package com.workflowManager.API;

import com.workflowManager.dto.schedule_dto.request.AllMyCalendarEventDto;
import com.workflowManager.dto.schedule_dto.request.AllMyTeamCalendarEventDto;
import com.workflowManager.service.schedule.CalendarService;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 주 기능 : 데이터베이스 정보를 api에 전달
 */
@RestController
@CrossOrigin //restApi에 붙여서 중복 및 충돌 방지하기
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class CalendarApiController {

    private final CalendarService calendarService;

    //팀, 개인 함께 조회


    // 게인 관련 메서드 ==================================================================

    // 개인의 캘린더 목록 전체 조회하기 (세션 사용)
    @GetMapping("/myEvents/All")
    public List<AllMyCalendarEventDto> getMyAllEvents(HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        return calendarService.getMyAllEvents(userId);
    }

    // 개인의 캘린더 목록 달에 따라서 조회하기 (세션 사용)
    @GetMapping("/myEvents")
    public List<AllMyCalendarEventDto> getMyEvents(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            HttpSession session) {

        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        try {
            return calendarService.getMyEventsForMonth(userId, year, month);
        } catch (Exception e) {
            log.error("Error fetching events for user: " + userId, e);
            throw new RuntimeException("Error fetching events");
        }
    }

    //개인 캘린더 일정 추가
    @PostMapping("/addEvent")
    public ResponseEntity<Map<String, Object>> createCalEvent(
            @RequestBody AllMyCalendarEventDto addEventDto,
            HttpSession session
    ) {
        String userName = LoginUtil.getLoggedInUser(session).getNickName();
        String userId = LoginUtil.getLoggedInUserAccount(session);

        // 비 로그인자 방지
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }

        boolean isSuccess = false;
        String message = "Failed to create event";

        try {
            // 클라이언트에서 전송된 날짜 데이터를 UTC로 변환하여 처리
            LocalDate eventDate = LocalDate.parse(addEventDto.getCalEventDate()); // ISO 8601 포맷의 문자열을 LocalDate로 변환
//            Instant calEventInstant = eventDate.atStartOfDay(ZoneOffset.UTC).toInstant();

            // UTC 시간으로 변환된 일정 데이터 생성
            AllMyCalendarEventDto newEvent = AllMyCalendarEventDto.builder()
                    .calEventDate(eventDate.toString()) // LocalDate를 문자열로 다시 저장
                    .calEventTitle(addEventDto.getCalEventTitle())
                    .calEventDescription(addEventDto.getCalEventDescription())
                    .calEventCreateAt(LocalDateTime.now())
                    .calEventUpdateAt(LocalDateTime.now())
                    .colorIndexId(addEventDto.getColorIndexId())
                    .userId(userId)
                    .userName(userName)
                    .build();

            // 서비스를 통해 일정 저장
            newEvent = calendarService.addEvent(newEvent, userId, userName);
            isSuccess = newEvent != null;
            message = isSuccess ? "Event created successfully" : message;
        } catch (Exception e) {
            message = e.getMessage();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", isSuccess);
        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    // 개인 캘린더 일정 수정하기
    @PostMapping("/updateEvent")
    public ResponseEntity<Map<String, Object>> editMyEvent(@RequestBody AllMyCalendarEventDto myCalendarEventDto, HttpSession session) {
        // Get userId from session
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User is not logged in"));
        }
        try {
            // Ensure calEventUpdateAt is not null before parsing
            if (myCalendarEventDto.getCalEventUpdateAt() == null) {
                // Handle the case where calEventUpdateAt is not provided by client
                // For example, you may choose to use the current time as updatedAt
                LocalDateTime updatedAt = LocalDateTime.now(); // Or any default value you prefer
                myCalendarEventDto.setCalEventUpdateAt(updatedAt);
            } else {
                // ISO-8601 형식으로 전송된 updateAt 문자열을 파싱
                LocalDateTime updatedAt = LocalDateTime.parse(myCalendarEventDto.getCalEventUpdateAt().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                myCalendarEventDto.setCalEventUpdateAt(updatedAt);
            }
            // Update the event with additional fields
            myCalendarEventDto.setUserId(userId);

            // 캘린더 서비스를 통해 이벤트 업데이트를 시도
            boolean success = calendarService.updateCalEvent(myCalendarEventDto);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "Event updated successfully"));
            } else {
                return ResponseEntity.status(400).body(Map.of("success", false, "message", "Failed to update event"));
            }
        } catch (Exception e) {
            log.error("Error updating event", e);
            return ResponseEntity.status(500).body(Map.of("message", "Error updating event"));
        }
    }

    //개인 캘린더 삭제
    @DeleteMapping("/deleteEvent/{calEventId}")
    public ResponseEntity<Map<String, Object>> deleteCalEvent(@PathVariable int calEventId, HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        try {
            boolean success = calendarService.deleteCalEvent(calEventId);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "Event deleted successfully"));
            } else {
                return ResponseEntity.status(400).body(Map.of("success", false, "message", "Failed to delete event"));
            }
        } catch (Exception e) {
            log.error("Error deleting event", e);
            return ResponseEntity.status(500).body(Map.of("message", "Error deleting event"));
        }
    }


    // 팀 관련 메서드 ==================================================================

    // 팀의 캘린더 목록 달에 따라서 조회하기 (세션 사용)
    @GetMapping("/myTeamEvents")
    public List<AllMyTeamCalendarEventDto> getMyTeamEvents(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            HttpSession session) {

        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        // 사용자의 부서 ID를 이용해 해당 부서의 특정 연도 및 월의 팀 캘린더 일정 목록을 조회함
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);

        log.info("부서아이디 조회해봅시다 api 확인 ~ : {}", departmentId);
        try {

            List<AllMyTeamCalendarEventDto> teamEventsForMonth = calendarService.getMyTeamEventsForMonth(departmentId, year, month);
            log.info("api에서 전달하는 json : {}",teamEventsForMonth);
            return teamEventsForMonth;

        } catch (Exception e) {
            log.error("Error fetching events for user: " + userId, e);
            throw new RuntimeException("Error fetching events");
        }
    }

    // 팀 캘린더 일정 추가
    @PostMapping("/addTeamEvent")
    public ResponseEntity<Map<String, Object>> createTeamEvent(
            @RequestBody AllMyCalendarEventDto addEventDto,
            HttpSession session
    ) {
        String userName = LoginUtil.getLoggedInUser(session).getNickName();
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);

        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }

        boolean isSuccess = false;
        String message = "Failed to create event";

        try {
            LocalDate eventDate = LocalDate.parse(addEventDto.getCalEventDate());
            // 새로운 팀 캘린더 이벤트 객체 생성
            AllMyTeamCalendarEventDto newTeamEvent = AllMyTeamCalendarEventDto.builder()
                    .calEventDate(eventDate.toString()) // LocalDate를 문자열로 다시 저장
                    .calEventTitle(addEventDto.getCalEventTitle())
                    .calEventDescription(addEventDto.getCalEventDescription())
                    .calEventCreateAt(LocalDateTime.now())
                    .calEventUpdateAt(LocalDateTime.now())
                    .colorIndexId(addEventDto.getColorIndexId())
                    .userId(userId)
//                    .teamCalendarId(teamCalId)
                    .departmentId(departmentId)
//                    .departmentName(addEventDto.getDepartmentName())
                    .userName(userName)
                    .updateBy(userName)
                    .build();

            // 사용자의 부서 ID를 이용해 해당 부서의 팀 캘린더에 이벤트 추가
            newTeamEvent = calendarService.addTeamEvent(newTeamEvent, userId, userName, departmentId);
            isSuccess = newTeamEvent != null;
            message = isSuccess ? "Event created successfully" : message;
        } catch (Exception e) {
            message = e.getMessage();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", isSuccess);
        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    // 팀 캘린더 일정 수정하기
    @PostMapping("/updateTeamEvent")
    public ResponseEntity<Map<String, Object>> editTeamEvent(@RequestBody AllMyTeamCalendarEventDto teamCalendarEventDto, HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        String userName = LoginUtil.getLoggedInUser(session).getNickName();
        String departmentId = LoginUtil.getLoggedInDepartmentId(session);
        Integer teamCalendarId = calendarService.getTeamId(departmentId);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User is not logged in"));
        }
        try {
            if (teamCalendarEventDto.getCalEventUpdateAt() == null) {
                LocalDateTime updatedAt = LocalDateTime.now();
                teamCalendarEventDto.setCalEventUpdateAt(updatedAt);
            } else {
                LocalDateTime updatedAt = LocalDateTime.parse(teamCalendarEventDto.getCalEventUpdateAt().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                teamCalendarEventDto.setCalEventUpdateAt(updatedAt);
            }
            teamCalendarEventDto.setUserId(userId);
            teamCalendarEventDto.setUpdateBy(userName);
            teamCalendarEventDto.setTeamCalendarId(teamCalendarId);

            // 팀 캘린더의 이벤트 업데이트 시도
            boolean success = calendarService.updateTeamCalEvent(teamCalendarEventDto);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "Event updated successfully"));
            } else {
                return ResponseEntity.status(400).body(Map.of("success", false, "message", "Failed to update event"));
            }
        } catch (Exception e) {
            log.error("Error updating team event", e);
            return ResponseEntity.status(500).body(Map.of("message", "Error updating team event"));
        }
    }

    // 팀 캘린더 삭제하기
    @DeleteMapping("/deleteTeamEvent/{calEventId}")
    public ResponseEntity<Map<String, Object>> deleteTeamEvent(@PathVariable int calEventId, HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        try {
            // 팀 캘린더의 특정 이벤트 삭제 시도
            boolean success = calendarService.deleteCalEvent(calEventId);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "Event deleted successfully"));
            } else {
                return ResponseEntity.status(400).body(Map.of("success", false, "message", "Failed to delete event"));
            }
        } catch (Exception e) {
            log.error("Error deleting team event", e);
            return ResponseEntity.status(500).body(Map.of("message", "Error deleting team event"));
        }
    }
}



