package com.workflowManager.API;

import com.workflowManager.entity.schedule.ColorIndex;
import com.workflowManager.entity.schedule.TeamTodoList;
import com.workflowManager.entity.schedule.TodoList;
import com.workflowManager.service.schedule.ColorIndexService;
import com.workflowManager.service.schedule.TodoListService;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/viewTodo")
public class TodoListViewController {

    private final TodoListService todoListService;
    private final ColorIndexService colorIndexService;

    // 개인의 투두리스트 조회 페이지
    @GetMapping("/personal")
    public String getMyPersonalTodos(
            HttpSession session,
            Model model
    ) {
        //세션으로 유저 아이디 조회
        String userId = LoginUtil.getLoggedInUserAccount(session);
        if (userId == null) {
            throw new RuntimeException("User is not logged in"); //로그인 안되었을 시 익셉션
        }
        try {
            // 유저 아이디의 투두리스트 조회
            List<TodoList> personalTodos = todoListService.findPersonalTodosByUserId(userId);
            model.addAttribute("personalTodos", personalTodos);
        } catch (Exception e) {
            // 제대로 서버 전달이 안되었을 경우 에러 메시지
            log.error("Error fetching events for user: " + userId, e);
            throw new RuntimeException("Error fetching events");
        }

        //컬러 인덱스 목록 가쟈오기
        List<ColorIndex> colorIndexes = colorIndexService.getAllColorIndexes();

        // 컬러 인덱스 아이디에 따른 해시코드 맵 생성
        Map<Integer, String> colorIndexMap = new HashMap<>();
        for (ColorIndex colorIndex : colorIndexes) {
            colorIndexMap.put(colorIndex.getColorIndexId(), colorIndex.getColorCode());
        }

        model.addAttribute("colorIndexMap", colorIndexMap);

        return "schedule/personalTodoList";

    }


    // 개인 투두리스트 추가 처리
    @PostMapping("/personal/add")
    public String addPersonalTodo(@ModelAttribute TodoList todoList) {
        todoListService.insertPersonalTodo(todoList);
        return "redirect:/todos/personal?userId=" + todoList.getUserId();
    }

    // 개인 투두리스트 수정 처리
    @PostMapping("/personal/edit/{todoId}")
    public String editPersonalTodo(@PathVariable int todoId, @ModelAttribute TodoList todoList) {
        todoList.setTodoId(todoId);
        todoListService.updatePersonalTodo(todoList);
        return "redirect:/todos/personal?userId=" + todoList.getUserId();
    }

    // 개인 투두리스트 삭제 처리
    @GetMapping("/personal/delete/{todoId}")
    public String deletePersonalTodo(@PathVariable int todoId, @RequestParam String userId) {
        todoListService.deletePersonalTodo(todoId);
        return "redirect:/todos/personal?userId=" + userId;
    }


    // 팀 투두리스트 조회 페이지
    @GetMapping("/team")
    public String getTeamTodos( HttpSession session,
                                Model model) {
        //세션으로 팀 아이디 조회
        String loggedInUser = LoginUtil.getLoggedInUserAccount(session);

        if (loggedInUser == null || loggedInUser.isEmpty()) {
            throw new RuntimeException("User is not logged in"); // 로그인 안된 경우 예외 처리
        }

        try {
            // 세션에서 departmentId 가져오기
            String loggedInDepartmentId = LoginUtil.getLoggedInDepartmentId(session);
            model.addAttribute("departmentId", loggedInDepartmentId);

            log.info("viewcontroller 에서 부서아이디 세션에서 가져옴 {}", loggedInDepartmentId);

            //팀 아이디로 투두리스트 조회
            List<TeamTodoList> teamTodos = todoListService.findTeamTodosByDepartmentId(loggedInDepartmentId);
            model.addAttribute("teamTodos", teamTodos);

        } catch (Exception e) {

            // 제대로 서버 전달이 안되었을 경우 에러 메시지
            log.error("Error fetching events for user: " + loggedInUser, e);
            throw new RuntimeException("Error fetching events");
        }

        return "schedule/teamTodoList";
    }


    // 팀 투두리스트 추가 처리
    @PostMapping("/team/add")
    public String addTeamTodo(
            @RequestBody TeamTodoList teamTodoList
    ) {
        todoListService.insertTeamTodo(teamTodoList);
        return "redirect:/todos/team?departmentId=" + teamTodoList.getDepartmentId();
    }


    // 팀 투두리스트 수정 처리
    @PostMapping("/team/edit/{teamTodoId}")
    public String editTeamTodo(@PathVariable int teamTodoId, @ModelAttribute TeamTodoList teamTodoList) {
        teamTodoList.setTeamTodoId(teamTodoId);
        todoListService.updateTeamTodo(teamTodoList);
        return "redirect:/todos/team?departmentId=" + teamTodoList.getDepartmentId();
    }

    // 팀 투두리스트 삭제 처리
    @GetMapping("/team/delete/{teamTodoId}")
    public String deleteTeamTodo(@PathVariable int teamTodoId, @RequestParam String departmentId) {
        todoListService.deleteTeamTodo(teamTodoId);
        return "redirect:/todos/team?departmentId=" + departmentId;
    }
}
