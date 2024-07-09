package com.workflowManager.service.schedule;

import com.workflowManager.entity.schedule.TeamTodoList;
import com.workflowManager.mapper.scheduleMapper.TodoListMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TodoListServiceTest {
    @Autowired
    private TodoListService todoListService;
    @Autowired
    private TodoListMapper todoListMapper;

    @Test
    @DisplayName("투두리스트 특정 부서 아이디로 리스트조회")
    void getTeamTodoT() {
        //given
        String departmentId = "dept1";
        //when
        List<TeamTodoList> teamTodosByDepartmentId = todoListMapper.findTeamTodosByDepartmentId(departmentId);
        //then
        teamTodosByDepartmentId.forEach(todo -> System.out.println(todo));

//        assertAll(
//                () -> assertEquals(2, teamTodosByDepartmentId.size(), "Size should be 2"),
//                () -> assertTrue(teamTodosByDepartmentId.stream().allMatch(todo -> todo.getDepartmentId().equals(departmentId)), "All todos should belong to the specified department")
//        );
    }
}