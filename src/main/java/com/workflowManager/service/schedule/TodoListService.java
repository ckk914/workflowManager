package com.workflowManager.service.schedule;

import com.workflowManager.entity.schedule.TeamTodoList;
import com.workflowManager.entity.schedule.TodoList;
import com.workflowManager.mapper.scheduleMapper.TodoListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoListService {
    private final TodoListMapper todoListMapper;

    // 특정 개인의 투두리스트 목록 하나 조회
    public TodoList findMyTodoById(int todoId) {
        return todoListMapper.findByTodoId(todoId);
    }


    // 특정 사용자의 개인 투두리스트 조회
    public List<TodoList> findPersonalTodosByUserId(String userId) {
        return todoListMapper.findByUserId(userId);
    }


    // 개인 투두리스트 추가
    public void insertPersonalTodo(TodoList todoList) {
        todoList.setTodoStatus("false"); // 기본 상태 설정
        todoList.setTodoCreateAt(LocalDateTime.now()); // 생성일 설정
        todoList.setTodoUpdateAt(LocalDateTime.now()); // 업데이트일 설정
        todoList.setColorIndexId(1); // 기본 색상 인덱스 설정
        todoListMapper.insertPersonalTodo(todoList);
    }


    // 개인 투두리스트 수정
    public void updatePersonalTodo(TodoList todoList) {
        todoList.setTodoUpdateAt(LocalDateTime.now()); // 업데이트일 설정
        todoListMapper.updatePersonalTodo(todoList);
    }


    // 개인 투두 삭제
    public void deletePersonalTodo(int todoId) {
        todoListMapper.deletePersonalTodo(todoId);
    }


    // 부서별 팀 투두 목록 조회
    public List<TeamTodoList> findTeamTodosByDepartmentId(String departmentId) {
        return todoListMapper.findTeamTodosByDepartmentId(departmentId);
    }


    // 특정 팀 투두 조회
    public TeamTodoList findTeamTodoById(int teamTodoId) {
        return todoListMapper.findTeamTodoById(teamTodoId);
    }


    // 팀 투두 추가
    public void insertTeamTodo(TeamTodoList teamTodoList) {
        teamTodoList.setTeamTodoStatus("false");
        teamTodoList.setTeamTodoCreateAt(LocalDateTime.now()); // 생성일 설정
        teamTodoList.setTeamTodoUpdateAt(LocalDateTime.now()); // 업데이트일 설정
        teamTodoList.setColorIndexId(1); // 기본 색상 인덱스 설정
        todoListMapper.insertTeamTodo(teamTodoList);
    }

    // 팀 투두 수정
    public void updateTeamTodo(TeamTodoList teamTodoList) {
        teamTodoList.setTeamTodoUpdateAt(LocalDateTime.now()); // 업데이트일 설정
        todoListMapper.updateTeamTodo(teamTodoList);
    }

    // 팀 투두 삭제
    public void deleteTeamTodo(int teamTodoId) {
        todoListMapper.deleteTeamTodo(teamTodoId);
    }


}

/*

//    // 모든 팀 투두리스트 조회
//    public List<TeamTodoList> findAllTeamTodos() {
//        return todoListMapper.findAllTeamTodos();
//    }


    //개인 투두리스트 모든 유저 모든 목록 전체 조회
    public List<TodoList> findAll() {
        return todoListMapper.findAll();
    }


    // 개인 투두리스트 추가
    public void insertPersonalTodo(TodoList todoList) {
        todoList.setTodoStatus("inprogress"); // Default status 설정
        todoList.setTodoCreateAt(LocalDateTime.now()); // 현재 시간으로 생성일 설정
        todoList.setTodoUpdateAt(LocalDateTime.now()); // 현재 시간으로 업데이트일 설정
        todoList.setColorIndexId(1); // Default color index 설정
        todoListMapper.insertPersonalTodo(todoList);
    }


    //개인 투두리스트 수정
    public void update(TodoList todoList) {
        todoListMapper.update(todoList);
    }

    // 개인 투두리스트 목록 하나 삭제
    public void delete(int todoId) {
        todoListMapper.delete(todoId);
    }

    //개인별 투두리스트 목록 전체 조회
    public List<TodoList> findByUserId(String userId) {
        return todoListMapper.findByUserId(userId);
    }

    //모든 팀 모든 투두리스트 목록 조회
    public List<TeamTodoList> findAllTeamTodos() {
        return todoListMapper.findAllTeamTodos();
    }


    // 팀 투두리스트 추가
    public void insertTeamTodo(TeamTodoList teamTodoList) {
        todoListMapper.insertTeamTodo(teamTodoList);
    }

    // 팀 투두리스트 수정
    public void updateTeamTodo(TeamTodoList teamTodoList) {
        todoListMapper.updateTeamTodo(teamTodoList);
    }

    //팀 투두리스트 삭제
    public void deleteTeamTodo(int teamTodoId) {
        todoListMapper.deleteTeamTodo(teamTodoId);
    }

    //부서아이디로 팀 투두리스트 목록 조회
    public List<TeamTodoList> findTeamTodosByDepartmentId(String departmentId) {
        return todoListMapper.findTeamTodosByDepartmentId(departmentId);
    }
}
 */
