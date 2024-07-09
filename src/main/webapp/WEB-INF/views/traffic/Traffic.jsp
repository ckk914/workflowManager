<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="../include/static-head.jsp" %> <%@ include
    file="../include/header.jsp" %>
    <meta charset="UTF-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"
    />
    <title>길찾기 결과 지도에 표출하기</title>
    <link rel="stylesheet" href="assets/css/traffic/traffic.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"
    />
    <script
      defer
      type="text/javascript"
      src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=nbpr5wd89w"
    ></script>

    <script
      defer
      type="text/javascript"
      src="assets/js/traffic/traffic.js"
    ></script>
    <script
      defer
      type="text/javascript"
      src="assets/js/traffic/weather.js"
    ></script>
    <script
      defer
      type="text/javascript"
      src="assets/js/traffic/metro.js"
    ></script>
  </head>
  <body>
    <div class="traffic-content">
      <form id="weather-from">
        <div id="weather" class="weather-box"></div>
      </form>

      <div class="container">
        <div class="map-container" id="map"></div>
        <div class="form-container">
          <div class="info-box">
            <form action="/traffic-Info" method="POST">
              <h1 class="section-header">
                경로설정<span id="before-find">
                  <a href="http://172.30.1.73:8383/traffic-myInfo"
                    ><i class="far fa-clipboard"></i
                  ></a>
                </span>
              </h1>

              <label for="departure-station">출발역</label>
              <select id="departure-station">
                <option disabled selected>출발역 선택</option>
                <c:forEach var="station" items="${stationInfo}">
                  <option
                    data-latitude="${station.latitude}"
                    data-line="${station.line}"
                    data-station="${station}"
                    data-longitude="${station.longitude}"
                    value="${station.stationName}"
                  >
                    ${station.line}호선 / ${station.stationName}
                  </option>
                </c:forEach>
              </select>
              <br />
              <label for="arrival-station">도착역</label>
              <select id="arrival-station">
                <option disabled selected>도착역 선택</option>
                <c:forEach var="station" items="${stationInfo}">
                  <option
                    data-latitude="${station.latitude}"
                    data-line="${station.line}"
                    data-longitude="${station.longitude}"
                    value="${station.stationName}"
                  >
                    ${station.line}호선 / ${station.stationName}
                  </option>
                </c:forEach>
              </select>

              <div>
                <label>총 역수: <span id="total-stations"></span>역</label>
              </div>
              <span>
                <label>총 소요시간: <span id="total-MetroTime"></span>분</label>
              </span>

              <h1 class="section-header">도착 예정 정보</h1>
              <div id="arrival-info" class="subway-map"></div>
              <div id="informationMetro" class="station-name"></div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <script>
      let stationInfo = `${stationInfo}`;
      let regex =
        /StationDto\(longitude=(.*?), latitude=(.*?), stationId=(.*?), stationName=(.*?), line=(.*?)\)/g;

      let stationInfoArray = [];

      let match;
      while ((match = regex.exec(stationInfo)) !== null) {
        let longitude = parseFloat(match[1]);
        let latitude = parseFloat(match[2]);
        let stationId = parseInt(match[3]);
        let stationName = match[4];
        let line = match[5];

        let stationObject = {
          longitude: longitude,
          latitude: latitude,
          stationId: stationId,
          stationName: stationName,
          line: line,
        };
        stationInfoArray.push(stationObject);
      }

      const infoStation = () => {
        const $departureStation = document.getElementById("departure-station");
        $departureStation.addEventListener("change", (e) => {
          // 초기화
          const informationMetro = document.getElementById("informationMetro");
          informationMetro.innerHTML = "";

          targetStation = e.target.value;
          const selectedOption = e.target.selectedOptions[0];

          dataStation = targetStation;
          dataLine = selectedOption.dataset.line;
          dataId = selectedOption.dataset.stationId;

          // 예시 배열
          const relativeIds = [5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5];
          // 결과를 저장할 객체
          const result = {};

          stationInfoArray.forEach((station) => {
            if (
              dataStation === station.stationName &&
              dataLine === station.line
            ) {
              let currentId = station.stationId;

              relativeIds.forEach((relativeId) => {
                let targetId = currentId + relativeId;

                let targetStation = stationInfoArray.find(
                  (station) => station.stationId === targetId
                );
                if (targetStation) {
                  result[targetId] = targetStation.stationName;
                }
              });
            }
          });

          for (const key in result) {
            if (result.hasOwnProperty(key)) {
              const stationName = result[key];

              const stationSpan = document.createElement("span");
              stationSpan.textContent = stationName;

              if (stationName === targetStation) {
                stationSpan.style.fontWeight = "bold";
                stationSpan.style.color = "black";
                stationSpan.style.fontSize = "15px";
              }

              informationMetro.appendChild(stationSpan);
            }
          }

          const $metroLine = document.getElementById("informationMetro");

          let metroLineColor = getSubwayLineColor(parseInt(dataLine));
          $metroLine.style.borderTop = "2px solid";
          $metroLine.style.borderColor = metroLineColor;
        });
      };

      infoStation();
    </script>
  </body>
</html>
