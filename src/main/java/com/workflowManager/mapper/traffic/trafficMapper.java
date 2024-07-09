package com.workflowManager.mapper.traffic;

import com.workflowManager.dto.traffic.request.totalTrafficInfoDto;
import com.workflowManager.dto.traffic.response.trafficInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface trafficMapper {

    // 선택 교통내용 저장
    boolean save(trafficInfoDto trafficInfo);

    // 선택한 교통내용 불러오기
    List<totalTrafficInfoDto> findAll(Map<String, Object> page);


    int count(String userId);

    List<totalTrafficInfoDto> findOne(String userId);

}
