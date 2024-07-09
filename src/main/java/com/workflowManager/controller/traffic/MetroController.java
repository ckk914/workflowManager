package com.workflowManager.controller.traffic;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflowManager.dto.traffic.request.StationDto;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


@Controller
@RequiredArgsConstructor
@CrossOrigin
public class MetroController {

    // 지하철 정보 컨트롤러
    @GetMapping("/traffic-map")
    public String metroInfo(Model model, HttpSession session) throws IOException {


        boolean loggedIn = LoginUtil.isLoggedIn(session);

        if(!loggedIn){
            return "redirect:/login";
        };


        String serviceKey = "xyigcn2H%2B16RENHs6SNbyOXjPjW0t0Tastu%2FePEl3PW6jMKcyrxrFErPO4Rzc%2BGgV2G44DvWYE%2FHGIeUhEIxCw%3D%3D";
        String uri = "https://api.odcloud.kr/api/15099316/v1/uddi:cfee6c20-4fee-4c6b-846b-a11c075d0987";
        uri += "?page=1&perPage=300&returnType=JSON&serviceKey=" + serviceKey;

        ArrayList<StationDto> stationArrayList = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON 응답을 JsonNode로 변환하여 필요한 필드만 추출
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                JsonNode dataNode = rootNode.path("data");

                stationArrayList = new ArrayList<>();
                for (JsonNode jsonNode : dataNode) {
                    StationDto stationBuild = StationDto.builder()
                            .longitude(jsonNode.path("경도").asText())
                            .latitude(jsonNode.path("위도").asText())
                            .stationId(jsonNode.path("고유역번호(외부역코드)").asInt())
                            .stationName(jsonNode.path("역명").asText())
                            .line(jsonNode.path("호선").asInt())
                            .build();

                    stationArrayList.add(stationBuild);
                }


                // 리스트값 가나다순으로 역 정렬
                Collections.sort(stationArrayList, Comparator.comparing(StationDto::getStationName));


                // System.out.println("stationArrayList: " + stationArrayList);
                model.addAttribute("stationInfo",stationArrayList);

            } else {
                System.out.println("GET request not worked");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "traffic/Traffic";
    }
}
