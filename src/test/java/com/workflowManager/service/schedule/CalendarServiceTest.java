package com.workflowManager.service.schedule;

import com.workflowManager.dto.schedule_dto.request.AllMyTeamCalendarEventDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Test
    @DisplayName("데이터 모두 디비에 있는대로 저장되어야 함 특히 userNAme")
    void findTest() {
        //given
        String departmentId = "D001";
        int year = 2024;
        int month = 7;

        //when
        List<AllMyTeamCalendarEventDto> myTeamEventsForMonth = calendarService.getMyTeamEventsForMonth(departmentId, year, month);

        //then
        for (AllMyTeamCalendarEventDto allMyTeamCalendarEventDto : myTeamEventsForMonth) {
            System.out.println("allMyTeamCalendarEventDto.getUserName() = " + allMyTeamCalendarEventDto.getUserName());
            
        }
        
        assertEquals("뭐뭐뭐", myTeamEventsForMonth.get(0).getUserName());
    }


}