let dataLine = 0;
let dataStation = "";
let targetStation = "";
let filterdData = [];

const $departureStation = document.getElementById("departure-station");
$departureStation.addEventListener("change", (e) => {
  targetStation = e.target.value;
  const selectedOption = e.target.selectedOptions[0];

  dataStation = targetStation;
  dataLine = selectedOption.dataset.line;

  // API 키와 엔드포인트 설정
  const apiKey = "4f65464c486e796f35377866586c48";
  const endpoint = `http://swopenapi.seoul.go.kr/api/subway/${apiKey}/json/realtimeStationArrival/0/5/${targetStation}`;

  fetch(endpoint)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then((data) => {
      filterdData = data.realtimeArrivalList.map((e) => ({
        barvlDt: e.barvlDt, // 열차도착예정시간(초)
        statnNm: e.statnNm, // 기차역이름
        updnLine: e.updnLine, // 상하행선구분
        arvlMsg2: e.arvlMsg2, // 첫번째도착메세지(도착, 출발, 진입 등)
        arvlMsg3: e.arvlMsg3, // 현재 지하철 위치
      }));

      // Update the DOM with the filtered data
      const $info = document.getElementById("arrival-info");
      const total = filterdData.length;
      $info.innerHTML = `<h2>도착예정수: ` + total + `건</h2>`;

      const trafficMap = [];

      const arrivalInfo = document.getElementById("informationMetro");
      const childElements = arrivalInfo.querySelectorAll(".station-name span");
      childElements.forEach((element) => {
        trafficMap.push(element.textContent);
      });

      let matchCount = 0;

      for (let i = 0; i < filterdData.length; i++) {
        const stationName = filterdData[i].arvlMsg3;
        const stationUpdnLine = filterdData[i].updnLine;
      
        for (let j = 0; j < trafficMap.length; j++) {
          if (
            stationName === trafficMap[j] ||
            trafficMap[j].includes(stationName)
          ) {
            matchCount++;
      
            const stationIcon = document.createElement("i");
            stationIcon.className = "fas fa-subway";
            stationIcon.style.position = "absolute";
            stationIcon.style.fontSize = "20px";
      
            // 색깔 설정
            if (stationUpdnLine === "상행") {
              stationIcon.style.color = "red";
            } else if (stationUpdnLine === "하행") {
              stationIcon.style.color = "blue";
            } else if (stationUpdnLine === "외선") {
              stationIcon.style.color = "green";
            } else {
              stationIcon.style.color = "black"; // 기본 색깔
            }
      
            const stationSpans = document.querySelectorAll(".station-name span");
            const positionLeft =
              stationSpans[j].offsetLeft + stationSpans[j].offsetWidth / 2 - 5;
      
            stationIcon.style.left = `${positionLeft}px`;
            stationIcon.style.top = "-20px"; // 아이콘을 역 이름 위에 배치
      
            stationSpans[j].parentNode.appendChild(stationIcon);
      
            // 상행, 하행, 외선 텍스트 추가
            const lineText = document.createElement("div");
            lineText.className = "line-text";
            lineText.textContent = stationUpdnLine;
            lineText.style.color = stationIcon.style.color;
            lineText.style.fontSize = "10px"; // 글자 크기 설정
            lineText.style.position = "absolute";
            lineText.style.left = `${positionLeft}px`;
            lineText.style.top = "-35px"; // 텍스트를 아이콘 위에 배치
            lineText.style.marginTop = "5px"; // 여백 추가
      
            stationSpans[j].parentNode.appendChild(lineText);
      
            // 문단과 마진 추가
            const paragraph = document.createElement("div");
            paragraph.style.marginTop = "25px"; // 문단과 아이콘 사이 여백 설정
            stationSpans[j].parentNode.appendChild(paragraph);
      
            break;
          }
        }
      }

      filterdData.forEach((e) => {
        const $infoDiv = document.createElement("div");
        const departureTime = parseInt(e.barvlDt / 60);
        $infoDiv.innerHTML = `
            <span>상/하행선: ${e.updnLine}</span>
            <span>현재 위치: ${e.arvlMsg2}</span>  
          `;
        $info.appendChild($infoDiv);
      });
      return filterdData;
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
});
