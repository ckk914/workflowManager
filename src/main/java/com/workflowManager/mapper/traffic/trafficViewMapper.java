package com.workflowManager.mapper.traffic;

import com.workflowManager.dto.traffic.response.StationViewResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface trafficViewMapper {

    boolean saveFavoriteStation(StationViewResponseDto userId);

    List<StationViewResponseDto> favoriteFindStation(String userId);

    List<StationViewResponseDto> findOne(String userId);

    boolean updateViewCounts(StationViewResponseDto dto);
}
