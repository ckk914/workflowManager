package com.workflowManager.mapper.scheduleMapper;

import com.workflowManager.entity.schedule.TeamTodoList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoListMapperTest {
    @Autowired
    TodoListMapper todoListMapper;

    @Test
    @DisplayName("부서아이디로 팀 투두리스트 조회좀 돼라")
    void getTeamTodoByDEPTID() {
        //given
        String departmentId = "D003";
        //when
        List<TeamTodoList> teamTodoLists = todoListMapper.findTeamTodosByDepartmentId(departmentId);
        //then
        teamTodoLists.forEach(teamTodoList -> System.out.println("teamTodoList = " + teamTodoList));

        assertAll(
//                () -> assertEquals(2, teamTodoLists.size(), "Size should be 2"),
                () -> assertTrue(teamTodoLists.stream().allMatch(todo -> todo.getDepartmentId().equals(departmentId)), "All todos should belong to the specified department")
        );
    }

}