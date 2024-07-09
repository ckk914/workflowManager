package com.workflowManager.service.traffic;



import com.workflowManager.common.traffic.myInfoPage;
import com.workflowManager.dto.traffic.request.totalTrafficInfoDto;
import com.workflowManager.dto.traffic.response.trafficInfoDto;
import com.workflowManager.mapper.traffic.trafficMapper;
import com.workflowManager.mapper.traffic.trafficViewMapper;
import com.workflowManager.util.LoginUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Builder
public class trafficService {

    private final trafficMapper trafficMapper;
    private final TrafficViewService trafficViewService;
    private final trafficViewMapper trafficViewMapper;

    public boolean save(trafficInfoDto trafficInfo, HttpSession session) {

        trafficInfoDto newTraffic = trafficInfo.builder()
                .userId(trafficInfo.getUserId())
                .arrival(trafficInfo.getArrival())
                .departure(trafficInfo.getDeparture())
                .station(trafficInfo.getStation())
                .needTime(trafficInfo.getNeedTime())
                .regDateTime(LocalDateTime.now())
                .build();

        String userId = LoginUtil.getLoggedInUser(session).getUserId();

        List<totalTrafficInfoDto> result = trafficMapper.findOne(userId);
//        List<StationViewResponseDto> result = trafficViewMapper.favoriteFindStation(userId);

        boolean foundExisting = false;

        for (totalTrafficInfoDto dto : result) {
            if (userId.equals(dto.getUserId())) {

                if (dto.getDeparture().equals(newTraffic.getDeparture()) && dto.getArrival().equals(newTraffic.getArrival())) {
                    trafficViewService.findOneAndUpdateViewCount(userId,newTraffic);

                    foundExisting = true;
                    break;
                }
            }
        }

        if (!foundExisting) {
            trafficViewService.save(userId, newTraffic);

        }


        return trafficMapper.save(newTraffic);
    }

    public List<totalTrafficInfoDto> findAll(myInfoPage page, HttpSession session, String sort) {
        String userId = LoginUtil.getLoggedInUser(session).getUserId();
        page.setUserId(userId);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("sort", sort);
        params.put("pageStart", page.getPageStart());
        params.put("amount", page.getAmount());

        return trafficMapper.findAll(params);
    }

    public int getCount(HttpSession session) {
        String userId = LoginUtil.getLoggedInUser(session).getUserId();

        return trafficMapper.count(userId);
    }
}