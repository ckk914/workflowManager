package com.workflowManager.API;

import com.workflowManager.dto.user.LoginUserInfoDto;
import com.workflowManager.entity.schedule.TeamTodoList;
import com.workflowManager.entity.schedule.TodoList;
import com.workflowManager.service.schedule.TodoListService;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin //restApi에 붙여서 중복 및 충돌 방지하기
@RequiredArgsConstructor
@RequestMapping("api/todos")
public class TodoListApiController {

    private final TodoListService todoListService;

    // Get user info from AngularJS
    @GetMapping("/user/info")
    public ResponseEntity<LoginUserInfoDto> getUserInfo(HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Retrieve user info
        LoginUserInfoDto loggedInUser = LoginUtil.getLoggedInUser(session);
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Return the first user info in the session list
        return ResponseEntity.ok(loggedInUser);
    }

    // Get personal todo list
    @GetMapping("/personal")
    public List<TodoList> getPersonalTodos(HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        try {
            return todoListService.findPersonalTodosByUserId(userId);
        } catch (Exception e) {
            log.error("Error fetching events for user: " + userId, e);
            throw new RuntimeException("Error fetching events");
        }
    }

    // Get specific personal todo item
    @GetMapping("/personal/aTodo/{todoId}")
    public ResponseEntity<TodoList> getPersonalOneTodos(@PathVariable int todoId) {
        TodoList myOneTodo = todoListService.findMyTodoById(todoId);
        return ResponseEntity.ok(myOneTodo);
    }

    // Add personal todo item
    @PostMapping("/personal")
    public ResponseEntity<TodoList> createPersonalTodo(@RequestBody TodoList todoList) {
        todoListService.insertPersonalTodo(todoList);
        return ResponseEntity.ok(todoList); // Or return the created TodoList object
    }

    // Update personal todo item
    @PutMapping("/personal/{todoId}")
    public ResponseEntity<TodoList> updatePersonalTodo(
            @PathVariable int todoId,
            @RequestBody TodoList updatedTodoList) {
        TodoList existingTodoList = todoListService.findMyTodoById(todoId);
        if (existingTodoList == null) {
            return ResponseEntity.notFound().build();
        }

        // Update fields based on updatedTodoList
        existingTodoList.setTodoContent(updatedTodoList.getTodoContent());
        existingTodoList.setTodoStatus(updatedTodoList.getTodoStatus());
        existingTodoList.setTodoUpdateAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        // Add more fields as needed

        todoListService.updatePersonalTodo(existingTodoList);
        return ResponseEntity.ok(existingTodoList);
    }

    // Delete personal todo item
    @DeleteMapping("/personal/{todoId}")
    public ResponseEntity<Void> deletePersonalTodo(@PathVariable int todoId) {
        todoListService.deletePersonalTodo(todoId);
        return ResponseEntity.noContent().build();
    }

    // Get team todo list by department ID
    @GetMapping("/team/aTeamTodos")
    public ResponseEntity<List<TeamTodoList>> findTeamTodosByDepartmentId(HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }
        try {
            String loggedInUserDept = LoginUtil.getLoggedInUser(session).getDepartmentId();
            log.info("부서로 로그인 함 : {}",loggedInUserDept);
            // Fetch todo list by department ID
            List<TeamTodoList> teamTodos = todoListService.findTeamTodosByDepartmentId(loggedInUserDept);
            return ResponseEntity.ok(teamTodos);
        } catch (Exception e) {
            log.error("Error fetching events for user: " + userId, e);
            throw new RuntimeException("Error fetching events");
        }
    }

    // Add team todo item
    @PostMapping("/team/aTeamTodos")
    public ResponseEntity<TeamTodoList> createTeamTodo(
            HttpSession session, // HttpSession 객체 주입
            @RequestBody TeamTodoList teamTodoList
    ) {
        // 세션에서 userId 가져오기
        String userId = LoginUtil.getLoggedInUserAccount(session);
        log.info("전달하려는 객체{}", teamTodoList);

        // userId가 null이 아닌지 로그로 확인
        if (userId != null) {
            System.out.println("User ID from session: " + userId);
        } else {
            System.out.println("User ID is null in session.");
        }

        // TodoListService를 사용하여 teamTodoList 저장
        todoListService.insertTeamTodo(teamTodoList);
        log.info("전달하려는 객체{}", teamTodoList);

        return ResponseEntity.ok(teamTodoList);
    }

    // Update team todo item
    @PutMapping("/team/{teamTodoId}")
    public ResponseEntity<TeamTodoList> updateTeamTodo(
            @PathVariable int teamTodoId,
            @RequestBody TeamTodoList teamTodoList) {
        TeamTodoList existingTeamTodoList = todoListService.findTeamTodoById(teamTodoId);
        if (existingTeamTodoList == null) {
            return ResponseEntity.notFound().build();
        }

        // Update fields based on teamTodoList
        existingTeamTodoList.setTeamTodoContent(teamTodoList.getTeamTodoContent());
        existingTeamTodoList.setTeamTodoStatus(teamTodoList.getTeamTodoStatus());
        existingTeamTodoList.setTeamTodoUpdateAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        // Add more fields as needed

        teamTodoList.setTeamTodoId(teamTodoId);
        todoListService.updateTeamTodo(existingTeamTodoList);
        return ResponseEntity.ok(existingTeamTodoList);
    }

    // Delete team todo item
    @DeleteMapping("/team/{teamTodoId}")
    public ResponseEntity<Void> deleteTeamTodo(@PathVariable int teamTodoId) {
        todoListService.deleteTeamTodo(teamTodoId);
        return ResponseEntity.noContent().build();
    }
}
