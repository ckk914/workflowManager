package com.workflowManager.mapper.scheduleMapper;

import com.workflowManager.dto.schedule_dto.request.AllMyCalendarEventDto;
import com.workflowManager.dto.schedule_dto.request.AllMyTeamCalendarEventDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalendarMapper {

    // 개인, 팀 공용 메서드 ========================================================

    // 로그인 시, 개인 캘린더 개수 확인 (새로 만들지 유무)
    Integer countPersonalCalendar(String userId);

    //로그인 시 개인 캘린더 없을 경우 개인 캘린더 아이디 부여
    void insertPersonalCalendar(Map<String, Object> params);

    //최초 로그인 시 teamCalendarId와 userId 연결해주는 중간 테이블 데이터 추가
    void insertUserTeamCalendar(Map<String, Object> teamParams);

    // 로그인 시, 팀 캘린더 개수 확인 (새로 만들지 유무) : 팀 스페이스를 무한으로 만들 수 있을 때의 업데이트 기능
    // Integer countUserTeamCalendar(String userId);

    //해당하는 팀 캘린더 유무 확인 : 업데이트 예정
    // TeamCalendar getTeamCalendarByDepartmentId(String departmentId);

    // 개인이 작성한 모든 달력 일정 조회
    List<AllMyCalendarEventDto> getMyAllCalendars(@Param("userId") String userId);

    // 개인, 팀 캘린더 일정 삭제
    void deleteCalendarEvent(int calEventId);

    // 개인, 팀 관련 개별 메서드 ========================================================

    //팀 캘린더 아이디 찾기
    Integer findMyTeamCalendarId(String departmentId);

    // 이번 달 개인 캘린더의 일정 가져오기
    List<AllMyCalendarEventDto> getCalendarEventsForPeriod(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    // 이번 달 팀 공유 캘린더의 일정 가져오기
    List<AllMyTeamCalendarEventDto> getTeamCalendarEventsForPeriod(@Param("departmentId") String departmentId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    // 개인 캘린더 전체 일정 목록 구하기
    List<AllMyCalendarEventDto> getMyAllCalendarEvents(@Param("userId") String userId);

    // 팀 캘린더 전체 공유일정 목록 구하기
    List<AllMyTeamCalendarEventDto> getMyAllTeamCalendarEvents(@Param("departmentId") String departmentId);

    //개인 일정 이벤트 아이디로 조회
    AllMyCalendarEventDto findOneMyCalEvent(int calEventId);

    //팀 공유일정 이벤트 아이디로 조회
    AllMyTeamCalendarEventDto findOneTeamCalEvent(int calEventId);

    // 개인 캘린더 일정 추가하기 : 서비스레이어
    void insertCalendarEvent(AllMyCalendarEventDto calendarEvent);

    // 팀 캘린더 일정 추가하기
    void insertTeamCalendarEvent(AllMyTeamCalendarEventDto teamCalendarEvent);

    // 개인 캘린더 일정 수정하기
    void updateMyCalEvent(AllMyCalendarEventDto calendarEvent);

    // 팀 캘린더 일정 수정하기
    void updateTeamCalEvent(AllMyTeamCalendarEventDto calendarEvent);


}
