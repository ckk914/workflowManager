package com.workflowManager.controller.traffic;

import com.workflowManager.common.traffic.myInfoPage;
import com.workflowManager.common.traffic.myPageMaker;
import com.workflowManager.dto.traffic.request.totalTrafficInfoDto;
import com.workflowManager.dto.traffic.response.trafficInfoDto;
import com.workflowManager.service.traffic.trafficService;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class TrafficController {

    private final trafficService trafficService;


    @PostMapping("/traffic-Info")
    public String trafficInformation(@RequestBody trafficInfoDto trafficInfo, HttpSession session){

        String userId = LoginUtil.getLoggedInUser(session).getUserId();

        try {
            trafficInfo.setUserId(userId);
            trafficService.save(trafficInfo,session);
        } catch (Exception e) {
            return "error/404";

        }


        return "traffic/Traffic";
    }

    @GetMapping("/traffic-myInfo")
    public String findTrafficInfo(Model model, myInfoPage page, HttpSession session, @RequestParam(value = "sort", required = false) String sort) {

        List<totalTrafficInfoDto> totalTraffic = null;
        try {
            totalTraffic = trafficService.findAll(page, session, sort);
        } catch (Exception e) {
            boolean loggedIn = LoginUtil.isLoggedIn(session);

            if(!loggedIn){
                return "redirect:/login";
            };
        }

        myPageMaker maker = new myPageMaker(page, trafficService.getCount(session));

        String userId = LoginUtil.getLoggedInUser(session).getUserId();
        maker.setUserId(userId);

        model.addAttribute("maker", maker);
        model.addAttribute("totalTraffic", totalTraffic);

        return "traffic/myTrafficInfo";
    }














}