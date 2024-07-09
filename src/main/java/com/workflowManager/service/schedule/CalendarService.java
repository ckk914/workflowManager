package com.workflowManager.service.schedule;

import com.workflowManager.dto.schedule_dto.request.AllMyCalendarEventDto;
import com.workflowManager.dto.schedule_dto.request.AllMyTeamCalendarEventDto;
import com.workflowManager.mapper.scheduleMapper.CalendarMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CalendarService {

    private final CalendarMapper calendarMapper;

    // ============================ 공유 서비스 메서드 ======================================

    // 개인, 팀 캘린더 일정 삭제
    public boolean deleteCalEvent(int calEventId) {
        try {
            calendarMapper.deleteCalendarEvent(calEventId);
            return true; // 삭제 성공 시 true 반환
        } catch (Exception e) {
            log.error("Failed to delete calendar event", e);
            return false; // 삭제 실패 시 false 반환
        }
    }

    // 해당 연도와 월의 마지막 날짜를 가져오는 헬퍼 메서드
    private int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    // 컬러인덱스 기본값 설정
    private Integer getDefaultColorIndex() {
        // Return default color index
        return 1; // 예시로 기본 색인 인덱스를 1로 설정
    }

    // user의 모든 캘린더 목록 - 캘릭더 아이디 유무 확인 목적
    public List<AllMyCalendarEventDto> getCalendars(String userId) {
        return calendarMapper.getMyAllCalendars(userId);
    }

    // 로그인 시 달력 유무 체크 : 업데이트 예정
    @Transactional
    public void checkAndSetupCalendars(String userId, String departmentId) {
        // 개인 캘린더가 존재하는지 확인
        Integer personalCalendarCount = calendarMapper.countPersonalCalendar(userId);
        if (personalCalendarCount == 0) {
            // 개인 캘린더 생성
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("calendarName", "Personal Calendar");
            calendarMapper.insertPersonalCalendar(params);
        }

        /*
        // 팀 캘린더와의 연결 확인 및 설정
        TeamCalendar teamCalendar = calendarMapper.getTeamCalendarByDepartmentId(departmentId);
    if (teamCalendar != null) {
        // 팀 캘린더를 사용자가 조회할 수 있도록 설정
        Integer userTeamCalendarCount = calendarMapper.countUserTeamCalendar(userId, teamCalendar.getTeamCalendarId());
        if (userTeamCalendarCount == 0) {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("teamCalendarId", teamCalendar.getTeamCalendarId());
            params.put("calendarName", "Team Calendar");
            calendarMapper.insertUserTeamCalendar(params);
             }
        }

         */
    }

    // ============================ 개인 개별 서비스 메서드 ======================================

    // 개인 캘린더 달별로 일정 목록 조회
    public List<AllMyCalendarEventDto> getMyEventsForMonth(String userId, int year, int month) {
        // startDate는 해당 연도의 해당 월의 첫 번째 날
        String startDate = String.format("%d-%02d-01", year, month);
        // endDate는 해당 연도의 해당 월의 마지막 날
        String endDate = String.format("%d-%02d-%02d", year, month, getLastDayOfMonth(year, month));
        return calendarMapper.getCalendarEventsForPeriod(userId, startDate, endDate);
    }

    // 개인 캘린더 일정 목록
    public List<AllMyCalendarEventDto> getMyAllEvents(String userId) {
        return calendarMapper.getMyAllCalendarEvents(userId);
    }

    //개인 캘린더 일정 추가
    @Transactional
    public AllMyCalendarEventDto addEvent(AllMyCalendarEventDto addEventDto, String userId, String userName) {
        LocalDateTime eventDateTime = LocalDate.parse(addEventDto.getCalEventDate()).atStartOfDay();

        AllMyCalendarEventDto newEvent = AllMyCalendarEventDto.builder()
                .calEventDate(eventDateTime.toString())
                .calEventTitle(addEventDto.getCalEventTitle())
                .calEventDescription(addEventDto.getCalEventDescription())
                .calEventCreateAt(LocalDateTime.now())
                .calEventUpdateAt(LocalDateTime.now())
                .userId(userId)
                .userName(userName)
                .colorIndexId(addEventDto.getColorIndexId())
                .build();

        calendarMapper.insertCalendarEvent(newEvent); // 실제로 이벤트를 삽입하는 코드
        return newEvent;
    }

    // 개인 캘린더 일정 수정
    @Transactional
    public boolean updateCalEvent(AllMyCalendarEventDto calendarEvent) {
        try {
            // 기존 이벤트를 가져오기
            AllMyCalendarEventDto existingEvent = calendarMapper.findOneMyCalEvent(calendarEvent.getCalEventId());

            // 업데이트할 필드 설정
            calendarEvent.setCalEventUpdateAt(LocalDateTime.now());

            //입력하지 않았을 경우 service에서 default value 설정
            if (calendarEvent.getColorIndexId() == null) {
                if (existingEvent.getColorIndexId() == null) {
                    calendarEvent.setColorIndexId(getDefaultColorIndex());
                } else {
                    calendarEvent.setColorIndexId(existingEvent.getColorIndexId());
                }
            }

            calendarMapper.updateMyCalEvent(calendarEvent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ============================ 팀 개별 서비스 메서드 ======================================

    // 팀 캘린더 달별로 일정 목록 조회
    public List<AllMyTeamCalendarEventDto> getMyTeamEventsForMonth(String departmentId, int year, int month) {
        // startDate는 해당 연도의 해당 월의 첫 번째 날
        String startDate = String.format("%d-%02d-01", year, month);
        // endDate는 해당 연도의 해당 월의 마지막 날
        String endDate = String.format("%d-%02d-%02d", year, month, getLastDayOfMonth(year, month));
        return calendarMapper.getTeamCalendarEventsForPeriod(departmentId, startDate, endDate);
    }


    //팀 캘린더 일정 추가
    @Transactional
    public AllMyTeamCalendarEventDto addTeamEvent(AllMyTeamCalendarEventDto addEventDto, String userId, String userName, String departmentId) {
        LocalDateTime eventDateTime = LocalDate.parse(addEventDto.getCalEventDate()).atStartOfDay();

        AllMyTeamCalendarEventDto newTeamEvent = AllMyTeamCalendarEventDto.builder()
                .calEventDate(eventDateTime.toString())
                .calEventTitle(addEventDto.getCalEventTitle())
                .calEventDescription(addEventDto.getCalEventDescription())
                .calEventCreateAt(LocalDateTime.now())
                .calEventUpdateAt(LocalDateTime.now())
                .colorIndexId(addEventDto.getColorIndexId())
                .userId(userId)
                .teamCalendarId(calendarMapper.findMyTeamCalendarId(departmentId))
                .departmentId(departmentId)
                .departmentName(addEventDto.getDepartmentName())
                .userName(userName)
                .updateBy(userName)
                .build();

        calendarMapper.insertTeamCalendarEvent(newTeamEvent);
        return newTeamEvent;
    }

//   팀 캘린더 일정 수정
    @Transactional
    public boolean updateTeamCalEvent(AllMyTeamCalendarEventDto teamCalendarEvent) {
        try {
            // 기존 이벤트를 가져오기
            AllMyTeamCalendarEventDto existingTeamEvent = calendarMapper.findOneTeamCalEvent(teamCalendarEvent.getCalEventId());

            if (existingTeamEvent == null) {
                // 기존 이벤트가 없을 경우 예외 처리 또는 적절한 로직 추가
                throw new IllegalArgumentException("Event not found for calEventId: " + teamCalendarEvent.getCalEventId());
            }

            // 업데이트할 필드 설정
            teamCalendarEvent.setCalEventUpdateAt(LocalDateTime.now());

            // 컬러인덱스 입력하지 않았을 경우 service에서 default value 설정
            if (teamCalendarEvent.getColorIndexId() == null) {
                teamCalendarEvent.setColorIndexId(existingTeamEvent.getColorIndexId() != null ? existingTeamEvent.getColorIndexId() : getDefaultColorIndex());
            }

            calendarMapper.updateTeamCalEvent(teamCalendarEvent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AllMyTeamCalendarEventDto> getAllTeamEvents(String departmentId) {
        return calendarMapper.getMyAllTeamCalendarEvents(departmentId);
    }

    //팀캘린더 조회하기
    public Integer getTeamId(String departmentId) {
        return calendarMapper.findMyTeamCalendarId(departmentId);
    }


    // 사용자의 부서 ID를 이용해 해당 부서의 특정 연도 및 월의 팀 캘린더 일정 목록을 조회함
//    public String getDepartmentIdByUserId(String userId) {
//
//    }
}
