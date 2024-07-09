var mapOptions = {
  center: new naver.maps.LatLng(37.5563, 126.9461), // 이대역 좌표
  zoom: 18,
};

var map = new naver.maps.Map("map", mapOptions);

let sx, sy, ex, ey;
let jsonResponse = {};

const departureStation = document.getElementById("departure-station");
const arrivalStation = document.getElementById("arrival-station");

let departureOption = "";
let arrivalOption = "";
let markers = [];
let polylines = [];

function clearMap() {
  markers.forEach((marker) => marker.setMap(null));
  markers = [];
  polylines.forEach((polyline) => polyline.setMap(null));
  polylines = [];
}

departureStation.addEventListener("change", (e) => {
  departureOption = e.target.selectedOptions[0];
  sx = parseFloat(departureOption.dataset.longitude);
  sy = parseFloat(departureOption.dataset.latitude);
  searchPubTransPathAJAX();
  clearMap();
});

arrivalStation.addEventListener("change", (e) => {
  arrivalOption = e.target.selectedOptions[0];
  ex = parseFloat(arrivalOption.dataset.longitude);
  ey = parseFloat(arrivalOption.dataset.latitude);
  searchPubTransPathAJAX();
  clearMap();
  if (sx === ex && sy === ey) {
    alert("출발지와 도착지가 같습니다.");
  }
});

function searchPubTransPathAJAX() {
  clearMap(); // 지도 초기화
  var xhr = new XMLHttpRequest();
  var url =
    "https://api.odsay.com/v1/api/searchPubTransPathT?SX=" +
    sx +
    "&SY=" +
    sy +
    "&EX=" +
    ex +
    "&EY=" +
    ey +
    "&apiKey=avqjby448YJDDqi4buwo5S69Uz5sf0nc3aEMdXWFzsQ";
  xhr.open("GET", url, true);
  xhr.send();
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      jsonResponse = JSON.parse(xhr.responseText);
      callMapObjApiAJAX(jsonResponse.result.path[0].info.mapObj);
      // console.log(jsonResponse);

      let totalSation = (document.getElementById("total-stations").textContent =
        jsonResponse.result.path[0].info.totalStationCount || "정보 없음");
      let totalTime = (document.getElementById("total-MetroTime").textContent =
        jsonResponse.result.path[0].info.totalTime || "정보 없음");
      trafficInfomation(totalSation, totalTime);
    }
  };
}

function callMapObjApiAJAX(mapObj) {
  var xhr = new XMLHttpRequest();
  var url =
    "https://api.odsay.com/v1/api/loadLane?mapObject=0:0@" +
    mapObj +
    "&apiKey=avqjby448YJDDqi4buwo5S69Uz5sf0nc3aEMdXWFzsQ";
  xhr.open("GET", url, true);
  xhr.send();
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      var resultJsonData = JSON.parse(xhr.responseText);
      drawNaverMarker(sx, sy); // 출발지 마커 표시
      drawNaverMarker(ex, ey); // 도착지 마커 표시
      drawNaverPolyLine(resultJsonData); // 노선 그래픽 데이터 지도 위 표시
      if (resultJsonData.result.boundary) {
        var boundary = new naver.maps.LatLngBounds(
          new naver.maps.LatLng(
            resultJsonData.result.boundary.top,
            resultJsonData.result.boundary.left
          ),
          new naver.maps.LatLng(
            resultJsonData.result.boundary.bottom,
            resultJsonData.result.boundary.right
          )
        );
        map.panToBounds(boundary);
      }
    }
  };
}

function drawNaverMarker(x, y) {
  var marker = new naver.maps.Marker({
    position: new naver.maps.LatLng(y, x),
    map: map,
    captionHaloColor: "red",
  });
  markers.push(marker);
}

function getSubwayLineColor(lineType) {
  switch (lineType) {
    case 1:
      return "#003499"; // 1호선
    case 2:
      return "#37b42d"; // 2호선
    case 3:
      return "#f36c21"; // 3호선
    case 4:
      return "#00a2d1"; // 4호선
    case 5:
      return "#af2796"; // 5호선
    case 6:
      return "#a05c1c"; // 6호선
    case 7:
      return "#54640d"; // 7호선
    case 8:
      return "#f14c82"; // 8호선
    case 9:
      return "#aa9872"; // 9호선
    default:
      return "#000000"; // 기본값 (검정색)
  }
}

function drawNaverPolyLine(data) {
  var lineArray;
  for (var i = 0; i < data.result.lane.length; i++) {
    for (var j = 0; j < data.result.lane[i].section.length; j++) {
      lineArray = [];
      for (var k = 0; k < data.result.lane[i].section[j].graphPos.length; k++) {
        lineArray.push(
          new naver.maps.LatLng(
            data.result.lane[i].section[j].graphPos[k].y,
            data.result.lane[i].section[j].graphPos[k].x
          )
        );
      }
      var polyline = new naver.maps.Polyline({
        map: map,
        path: lineArray,
        strokeWeight: 3,
        strokeColor: getSubwayLineColor(data.result.lane[i].type),
      });
      polylines.push(polyline);
    }
  }
}

let timeoutId;

function trafficInfomation(totalStation, totalTime) {
  // 기존 타이머가 있으면 취소
  if (timeoutId) {
    clearTimeout(timeoutId);
  }

  // 새로운 타이머 설정 (500ms 지연)
  timeoutId = setTimeout(() => {
    const station = totalStation;
    const time = totalTime;
    const departure = departureOption.textContent.trim();
    const arrival = arrivalOption.textContent.trim();

    // 객체 생성
    const trafficInfo = {
      station: station,
      needTime: time,
      departure: departure,
      arrival: arrival,
    };

    // JSON 문자열로 변환 및 서버로 전송
    const trafficInfoJson = JSON.stringify(trafficInfo);

    fetch('/traffic-Info', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: trafficInfoJson
    })
    .then(response => response.json())
    .then(data => {
      console.log('Success:', data);
    })
    .catch((error) => {
      console.error('Error:', error);
    })
  }, 2500);  // 500ms 지연 시간

}

