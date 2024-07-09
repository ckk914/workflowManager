package com.workflowManager.API;

import com.workflowManager.service.schedule.CalendarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalendarViewControllerTest {

    @Autowired
    CalendarService calendarService;

    @Test
    @DisplayName("팀캘린더 아이디 찾기")
    void teamCalIDfind () {
        //given
        String departmentId = "D002";

        //when
        Integer teamId = calendarService.getTeamId(departmentId);

        //then
        assertEquals(2, teamId);
    }

}