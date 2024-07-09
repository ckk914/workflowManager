package com.workflowManager.mapper.userMapper;

import com.workflowManager.dto.DepartmentNameDto;

import com.workflowManager.dto.user.AutoLoginDto;
import com.workflowManager.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    //부서 조회
    List<DepartmentNameDto> findDepartmentAll();

    //회원가입
    boolean save(User user);

    //회원 정보 개별 조회
    User findOne(String userId);


    boolean existsById(@Param("type") String type,
                       @Param("keyword") String keyword);
    //자동로그인 쿠키값, 만료 시간 업데이트
    void updateAutoLogin(AutoLoginDto dto);
    //세션 아이디로 회원정보 조회
    User findMemberBySessionId(String sessionId);


    //유저이름 조회하기
    String findUserName (String userId);

    //⎏ 윤종 추가
    //부서 이름 하나 조회하기
    String findOneDepartmentName(String departmentId);

    // 비밀번호 변경
    boolean UserSetPassword(@Param("password")String password,@Param("userId")String userId);
}
