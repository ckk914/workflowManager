package com.workflowManager.controller.traffic;

import com.workflowManager.dto.traffic.response.StationViewResponseDto;
import com.workflowManager.service.traffic.TrafficViewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
public class TrafficViewController {

    private final TrafficViewService trafficViewService;

    @GetMapping("/traffic-rank")
    public String rankedView(Model model, HttpSession session){

        List<StationViewResponseDto> favoriteView = trafficViewService.view(session);
        model.addAttribute("favoriteView", favoriteView);

        return "traffic/FavoriteTraffic";
    }
}
