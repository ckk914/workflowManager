package com.workflowManager.mapper.scheduleMapper;

import com.workflowManager.dto.schedule_dto.request.AllMyTeamTodoListDto;
import com.workflowManager.entity.schedule.TeamTodoList;
import com.workflowManager.entity.schedule.TodoList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoListMapper {

    // 개인별 투두리스트 조회
//    @Select("SELECT * FROM TodoList WHERE user_id = #{userId}")
    List<TodoList> findByUserId(String userId);

    // 개인 투두리스트 추가
//    @Insert("INSERT INTO TodoList (todo_content, todo_status, todo_createAt, todo_updateAt, color_index_id, user_id) " +
//            "VALUES (#{todoContent}, #{todoStatus}, #{todoCreateAt}, #{todoUpdateAt}, #{colorIndexId}, #{userId})")
    void insertPersonalTodo(TodoList todoList);

    // 개인 투두리스트 수정
//    @Update("UPDATE TodoList SET todo_content = #{todoContent}, todo_status = #{todoStatus}, " +
//            "todo_updateAt = #{todoUpdateAt}, color_index_id = #{colorIndexId} " +
//            "WHERE todo_id = #{todoId}")
    void updatePersonalTodo(TodoList todoList);

    // 개인 투두리스트 삭제
//    @Delete("DELETE FROM TodoList WHERE todo_id = #{todoId}")
    void deletePersonalTodo(int todoId);

    // 팀 투두리스트 추가
    void insertTeamTodo(TeamTodoList teamTodoList);

    // 팀 투두리스트 수정
//    @Update("UPDATE TeamTodoList SET team_todo_content = #{teamTodoContent}, team_todo_status = #{teamTodoStatus}, " +
//            "team_todo_updateAt = #{teamTodoUpdateAt}, color_index_id = #{colorIndexId} " +
//            "WHERE team_todo_id = #{teamTodoId}")
    void updateTeamTodo(TeamTodoList teamTodoList);

    // 팀 투두리스트 삭제
//    @Delete("DELETE FROM TeamTodoList WHERE team_todo_id = #{teamTodoId}")
    void deleteTeamTodo(int teamTodoId);



    // 부서별 팀 투두리스트 조회
//    @Select("SELECT * FROM TeamTodoList WHERE department_id = #{departmentId}")
    List<TeamTodoList> findTeamTodosByDepartmentId(String departmentId);

    //    // 특정 팀투두리스트 목록 하나 투두아이디로 조회
////    @Select("SELECT * FROM TeamTodoList WHERE team_todo_id = #{teamTodoId}")
    TeamTodoList findTeamTodoById(int teamTodoId);

    //    // 특정한 투두리스트 투두아이디로 조회 : 이 기능은 없어도 될 것 같은데
////    @Select("SELECT * FROM TodoList WHERE todo_id = #{todoId}")
    TodoList findByTodoId(int todoId);

    //팀 투두리스트로 status, 등록일자로  정렬
    List<AllMyTeamTodoListDto> alignTeamTodoStatus(String departmentId);
}
/*
    // 개인 투두리스트 추가
//    @Insert("INSERT INTO TodoList (todo_content, todo_status, todo_createAt, todo_updateAt, color_index_id, user_id) " +
//            "VALUES (#{todoContent}, #{todoStatus}, #{todoCreateAt}, #{todoUpdateAt}, #{colorIndexId}, #{userId})")
    void insertPersonalTodo(TodoList todoList);

    // 개인 투두리스트 수정
//    @Update("UPDATE TodoList SET todo_content = #{todoContent}, todo_status = #{todoStatus}, " +
//            "todo_updateAt = #{todoUpdateAt}, color_index_id = #{colorIndexId} " +
//            "WHERE todo_id = #{todoId}")
    void updatePersonalTodo(TodoList todoList);

    // 개인 투두리스트 삭제
//    @Delete("DELETE FROM TodoList WHERE todo_id = #{todoId}")
    void deletePersonalTodo(int todoId);

    // 팀 투두리스트 추가
//    @Insert("INSERT INTO TeamTodoList (team_todo_content, team_todo_status, team_todo_createAt, " +
//            "team_todo_updateAt, color_index_id, user_id, department_id) " +
//            "VALUES (#{teamTodoContent}, #{teamTodoStatus}, #{teamTodoCreateAt}, #{teamTodoUpdateAt}, " +
//            "#{colorIndexId}, #{userId}, #{departmentId})")
    void insertTeamTodo(TeamTodoList teamTodoList);

    // 팀 투두리스트 수정
//    @Update("UPDATE TeamTodoList SET team_todo_content = #{teamTodoContent}, team_todo_status = #{teamTodoStatus}, " +
//            "team_todo_updateAt = #{teamTodoUpdateAt}, color_index_id = #{colorIndexId} " +
//            "WHERE team_todo_id = #{teamTodoId}")
    void updateTeamTodo(TeamTodoList teamTodoList);

    // 팀 투두리스트 삭제
//    @Delete("DELETE FROM TeamTodoList WHERE team_todo_id = #{teamTodoId}")
    void deleteTeamTodo(int teamTodoId);

    // 개인별 투두리스트 조회
//    @Select("SELECT * FROM TodoList WHERE user_id = #{userId}")
    List<TodoList> findByUserId(String userId);

    // 부서별 팀 투두리스트 조회
//    @Select("SELECT * FROM TeamTodoList WHERE department_id = #{departmentId}")
    List<TeamTodoList> findTeamTodosByDepartmentId(String departmentId);

//    // 개인별 투두리스트 조회
////    @Select("SELECT * FROM TodoList WHERE user_id = #{userId}")
//    List<TodoList> findByUserId(String userId);
//
//    // 개인 투두리스트 모든 사람 목록 전체 조회
////    @Select("SELECT * FROM TodoList")
//    List<TodoList> findAll();
//

//
//    // 개인 투두리스트 추가
////    @Insert("INSERT INTO TodoList(todo_content, todo_status, color_index_id, user_id) " +
////            "VALUES(#{todoContent}, #{todoStatus}, #{colorIndexId}, #{userId})")
////    @Options(useGeneratedKeys = true, keyProperty = "todoId")
//    void insert(TodoList todoList);
//
//    // 개인 투두리스트 수정
////    @Update("UPDATE TodoList SET todo_content=#{todoContent}, todo_status=#{todoStatus}, " +
////            "color_index_id=#{colorIndexId}, user_id=#{userId} WHERE todo_id=#{todoId}")
//    void update(TodoList todoList);
//
//    // 개인 투두리스트 삭제
////    @Delete("DELETE FROM TodoList WHERE todo_id=#{todoId}")
//    void delete(int todoId);
//
//    // 팀 투두리스트 전체 조회
////    @Select("SELECT * FROM TeamTodoList")
//    List<TeamTodoList> findAllTeamTodos();
//

//
//    // 팀 투두리스트 추가
////    @Insert("INSERT INTO TeamTodoList(team_todo_content, team_todo_status, color_index_id, user_id, department_id) " +
////            "VALUES(#{teamTodoContent}, #{teamTodoStatus}, #{colorIndexId}, #{userId}, #{departmentId})")
////    @Options(useGeneratedKeys = true, keyProperty = "teamTodoId")
//    void insertTeamTodo(TeamTodoList teamTodoList);
//
//    // 팀 투두리스트 수정
////    @Update("UPDATE TeamTodoList SET team_todo_content=#{teamTodoContent}, team_todo_status=#{teamTodoStatus}, " +
////            "color_index_id=#{colorIndexId}, user_id=#{userId}, department_id=#{departmentId} " +
////            "WHERE team_todo_id=#{teamTodoId}")
//    void updateTeamTodo(TeamTodoList teamTodoList);
//
//    // 팀 투두리스트 삭제
////    @Delete("DELETE FROM TeamTodoList WHERE team_todo_id=#{teamTodoId}")
//    void deleteTeamTodo(int teamTodoId);
//
//    // 부서별 팀 투두리스트 목록 조회
////    @Select("SELECT * FROM TeamTodoList WHERE department_id = #{departmentId}")
//    List<TeamTodoList> findTeamTodosByDepartmentId(String departmentId);
}

 */
