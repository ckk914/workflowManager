<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>team Calendar</title>
    <%--  css--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" href="../assets/css/maincalendar.css">
        <style>
            body {
                font-family: "Spoqa Han Sans Neo", sans-serif;
                margin: 0 auto;
                padding: 0;
                box-sizing: border-box;
                background-color: #f2efeb;
            }
        .team-name {
            position: absolute;
            color: gray;
            bottom: 10px;
            margin: 4px 10px;
        }

        .color-picker div.selected {
            border: 2px solid #000; /* 선택된 색상 주위에 선 추가 */
        }

    </style>
</head>
<body>

<!-- 이벤트 상세 및 일정 수정 모달 -->
<div id="eventModal" class="modal">
    <div class="modal-content">
        <span class="close"><i class="fa-solid fa-x"></i></span>
        <i class="fa-regular fa-trash-can" style="color: #929292;" id="deleteEvent"></i>
        <i class="fa-solid fa-pencil" style="color: #444444;" id="editEvent"></i>
        <ul id="eventDetails">
            <%--    수정버튼을 눌러야 버튼이 보여짐--%>
        </ul>
        <button id="saveChangesButton" style="display:none;">Save Changes</button>
    </div>
</div>

<%-- 일정 추가 모달 --%>
<div id="addEventModal" class="modal">
    <div class="modal-content">
        <span class="close"><i class="fa-solid fa-x"></i></span>
        <button class="fa-add" type="button" id="saveEventButton">Add</button>
        <h2>New Event</h2>
        <form id="addEventForm">
            <label for="calEventTitle">Title</label>
            <input type="text" id="calEventTitle" name="calEventTitle" placeholder="Event"><br>

            <label for="calEventDate">Date</label>


            <input type="date" id="calEventDate" name="calEventDate" value="\${fn:substring(new java.text.SimpleDateFormat('yyyy-MM-dd').format(new java.util.Date()), 0, 10)}}"><br>

            <label for="calEventDescription">Event</label>
            <input type="text" id="calEventDescription" name="calEventDescription" placeholder="None"><br>

            <label for="calColorIndex"></label>
            <div class="color-picker">
                <div class="color-lightsteelblue" data-color-index="1"></div>
                <div class="color-darkslateblue" data-color-index="2"></div>
                <div class="color-steelblue" data-color-index="3"></div>
                <div class="color-lightyellow" data-color-index="4"></div>
                <div class="color-lightpink" data-color-index="5"></div>
            </div>
            <input type="hidden" id="calColorIndex" name="calColorIndex" value=""><br>

        </form>
    </div>
</div>

<%--달력 화면--%>
<div class="calendar-container">
    <div class="calendar-header">
        <div class="calendar-month">
        <i id="prev-month" class="fa-solid fa-caret-left"></i>
        <h3 id="current-month"></h3>
        <i id="next-month" class="fa-solid fa-caret-right"></i>
    </div>
        <div class="calendar-add-btn">
            <i class="fa-regular fa-calendar-plus"></i>
        </div>
    </div>
    <div id="calendar"></div>
</div>

<script>
    // JSON 형식의 문자열을 자바스크립트 객체로 반환하기
    const nowUserNameFromServer = "${userName}";
    const nowDepartmentIdFromServer = "${departmentId}";
    const nowTeamIdFromServer =  '<c:out value="${teamCalendarId}" />';

    console.log("userName, departmentId, nowteamcalId >>>>",nowUserNameFromServer, nowDepartmentIdFromServer, nowTeamIdFromServer ? nowTeamIdFromServer : "null임");

    let myTeamCalEvents = []; // API에서 받은 데이터를 저장할 배열

    const teamMonthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    let teamCurrentYear = new Date().getFullYear();
    let teamCurrentMonth = new Date().getMonth();

    // 초기 데이터 로드
    fetchTeamEvents(teamCurrentYear, teamCurrentMonth);

    function fetchTeamEvents(year, month) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `/api/calendar/myTeamEvents?year=\${year}&month=\${month + 1}`, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const teamData = JSON.parse(xhr.responseText); // JSON 데이터를 파싱하여 객체로 변환

                    myTeamCalEvents = teamData; // 받은 데이터를 전역 변수에 저장

                    renderTeamCalendar(myTeamCalEvents, year, month);
                } else {
                    console.error('Failed to fetch calendar events:', xhr.status, xhr.statusText);
                }
            }
        };

        xhr.onerror = function () {
            console.error('Failed to fetch calendar events: Network Error');
        };

        xhr.send();
    }

    renderTeamCalendar(myTeamCalEvents, teamCurrentYear, teamCurrentMonth);


    //화면에 달력 데이터 렌더링
    function renderTeamCalendar(events, year, month) {
        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();

        let teamCalendarHtml = '<table class="calendar">';
        teamCalendarHtml += '<tr>';
        teamCalendarHtml += '<th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th>';
        teamCalendarHtml += '</tr><tr>';

        for (let i = 0; i < firstDay; i++) {
            teamCalendarHtml += '<td></td>';
        }

        for (let date = 1; date <= lastDate; date++) {
            const day = (firstDay + date - 1) % 7;
            if (day === 0 && date > 1) {
                teamCalendarHtml += '</tr><tr>';
            }

            const yearStr = year.toString();
            const monthStr = (month + 1 < 10 ? '0' + (month + 1) : month + 1).toString();
            const dateStr = (date < 10 ? '0' + date : date).toString();

            const fullDateStr = `\${yearStr}-\${monthStr}-\${dateStr}`;

            teamCalendarHtml += `<td><div>\${date}</div>`;

            // 해당 날짜에 해당하는 이벤트 필터링
            const filteredTeamEvents = events.filter(event => event.calEventDate.startsWith(fullDateStr));

            if (filteredTeamEvents.length > 0) {

                filteredTeamEvents.forEach(event => {

                    console.log('Rendering event:', event); // Add debug logging

                    const colorClass = `event-\${getTeamColorByIndex(event.colorIndexId)}`;
                    teamCalendarHtml += `<div class="event \${colorClass}" onclick="openTeamModal(\${event.calEventId})">\${event.calEventTitle}</div>`;
                });
            }

            teamCalendarHtml += '</td>';
        }

        teamCalendarHtml += '</tr></table>';

        document.getElementById('calendar').innerHTML = teamCalendarHtml;
        updateTeamCurrentMonth(year, month);
    }

    // 현재 날짜 업데이트 함수
    function updateTeamCurrentMonth(year, month) {
        document.getElementById('current-month').textContent = `\${teamMonthNames[month]} \${year}`;
    }

    // colorindex에 따라 색 부여하기
    function getTeamColorByIndex(index) {

        const colors = {
            1: 'lightsteelblue',
            2: 'darkslateblue',
            3: 'steelblue',
            4: 'lightyellow',
            5: 'lightpink',
        };
                return colors[parseInt(index)] || 'default-color'; // index를 정수로 변환 후 처리
    }

    // Event Modal 닫기
    document.addEventListener('click', function (event) {
        const teamModal = document.getElementById('eventModal');
        const addEventModal = document.getElementById('addEventModal');

        if (event.target === teamModal || event.target === addEventModal) {
            teamModal.style.display = 'none';
            addEventModal.style.display = 'none';
        }
    });

    // 일정 추가 모달 열기
    document.querySelector('.fa-calendar-plus').addEventListener('click', function () {
        const addEventModal = document.getElementById('addEventModal');
        addEventModal.style.display = 'block';

        // 초기화 로직 추가 (모달이 열릴 때 입력 필드 초기화)
        document.getElementById('calEventTitle').value = '';
        document.getElementById('calEventDate').value = '';
        document.getElementById('calEventDescription').value = '';
        document.getElementById('calColorIndex').value = '';

        // 색상 선택 기능
        document.querySelectorAll('.color-picker div').forEach(function (colorDiv) {
            colorDiv.addEventListener('click', function () {
                document.getElementById('calColorIndex').value = this.getAttribute('data-color-index');
                updateTeamColorPreview(document.getElementById('calColorIndex').value); // Update color preview
            });
        });

        const closeBtn = addEventModal.querySelector('.close');
        closeBtn.onclick = function () {
            addEventModal.style.display = 'none';
        };
    });

    // 색상 미리보기 업데이트 함수
    function updateTeamColorPreview(index) {
        const colorDivs = document.querySelectorAll('.color-picker div');
        colorDivs.forEach(function (colorDiv) {
            colorDiv.classList.remove('selected');
            if (colorDiv.getAttribute('data-color-index') === index) {
                colorDiv.classList.add('selected');
            }
        });
    }


    // 이벤트 상세보기
    function openTeamModal(eventId) {
        console.log('openModal called with eventId:', eventId);

        const selectedEvent = myTeamCalEvents.find(event => event.calEventId === eventId);
        console.log('Selected Event:', selectedEvent);

        const teamModal = document.getElementById('eventModal');
        const modalContent = teamModal.querySelector('.modal-content');
        const eventDetails = teamModal.querySelector('#eventDetails');
        // 수정자 표시
        let updateByMessage = selectedEvent.updateBy ? selectedEvent.updateBy : "아직 아무도 수정하지 않았어요!";

        // 날짜에서 시간 부분 제거
        const eventDateWithoutTime = selectedEvent.calEventDate.split('T')[0];


        //수정하고 난 뒤에만 수정자 표시
        eventDetails.innerHTML = `
        <li><strong>Title :</strong> \${selectedEvent.calEventTitle}</li>
        <li><strong>Event description :</strong> \${selectedEvent.calEventDescription}</li>
        <li><strong>Event Date :</strong> \${eventDateWithoutTime}</li>
        <li><strong>Write By :</strong> \${selectedEvent.userName}</li>
        <li><strong>Edited By :</strong> \${updateByMessage}</li>
      `;

        const editButton = teamModal.querySelector('#editEvent');
        const saveChangesButton = teamModal.querySelector('#saveChangesButton');

        editButton.onclick = function () {
            const titleSpan = eventDetails.querySelector('#eventDetails li:nth-child(1)');
            const descriptionSpan = eventDetails.querySelector('#eventDetails li:nth-child(2)');
            const dateSpan = eventDetails.querySelector('#eventDetails li:nth-child(3)');

            titleSpan.innerHTML = `<input type="text" id="edit-title" value="\${selectedEvent.calEventTitle}">`;
            descriptionSpan.innerHTML = `<input type="text" id="edit-description" value="\${selectedEvent.calEventDescription}">`;
            dateSpan.innerHTML = `<input type="date" id="edit-date" value="\${selectedEvent.calEventDate}">`;

            saveChangesButton.style.display = 'block';
        };

        // 일정 수정 정보 저장하기
        saveChangesButton.onclick = function () {
            const updatedTitle = document.getElementById('edit-title').value;
            const updatedDate = document.getElementById('edit-date').value;
            const updatedDescription = document.getElementById('edit-description').value;

            //수정 안됨 버그 픽스
            if (updatedTitle && updatedDate) {
                const updateEvent = {
                    calEventId: selectedEvent.calEventId,
                    calEventTitle: updatedTitle,
                    calEventDate: updatedDate,
                    calEventDescription: updatedDescription,
                    calColorIndex: selectedEvent.calColorIndex,
                    updateBy: nowUserNameFromServer,
                };

                //색상 바로 렌더링
                updateTeamColorPreview();

                console.log("수정 데이터 ", updateEvent);

                //일정 수정
                fetch('/api/calendar/updateTeamEvent', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(updateEvent),
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            // 수정 시 작성자도 바뀌는 이유
                            selectedEvent.calEventTitle = updatedTitle;
                            selectedEvent.calEventDate = updatedDate;
                            selectedEvent.calEventDescription = updatedDescription;
                            selectedEvent.updateBy = nowUserNameFromServer; // 수정자 데이터 추가

                            // Close modal and re-render calendar
                            teamModal.style.display = 'none';
                            //입력한 일정으로 달력화면 보여주기
                            renderTeamCalendar(myTeamCalEvents, new Date(updatedDate).getFullYear(), new Date(updatedDate).getMonth());
                        } else {
                            alert('로그인이 필요한 서비스 입니다.');
                        }
                    })

                    .catch(error => {
                        console.error('Error:');
                        alert('로그인이 필요한 서비스 입니다.');
                    });
            }
            else {
                alert('제목과 날짜를 입력하세요.');
            }
        };
        // 모달 보이기
        teamModal.style.display = 'block';

        // 저장 후 Save Changes 버튼 다시 숨기기
        saveChangesButton.style.display = 'none';

        const closeBtn = teamModal.querySelector('.close');
        closeBtn.onclick = function () {
            teamModal.style.display = 'none';
        };

    // 모달 외부를 클릭하면 닫기
        window.onclick = function (event) {
            if (event.target === teamModal) {
                teamModal.style.display = 'none';
                saveChangesButton.style.display = 'none';
            }
        };

        //일정 삭제하기
        const deleteButton = teamModal.querySelector('#deleteEvent');
        deleteButton.onclick = function () {
            console.log(selectedEvent.calEventId); // 이벤트 아이디 잘 나옴
            if (confirm('정말 이 이벤트를 삭제하시겠습니까?')) {
                fetch(`/api/calendar/deleteEvent/\${selectedEvent.calEventId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({calEventId: selectedEvent.calEventId}),
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            // 성공적으로 삭제된 경우
                            const index = myTeamCalEvents.findIndex(event => event.calEventId === selectedEvent.calEventId);
                            if (index !== -1) {
                                myTeamCalEvents.splice(index, 1);
                            }
                            teamModal.style.display = 'none';
                            renderTeamCalendar(myTeamCalEvents, new Date(selectedEvent.calEventDate).getFullYear(), new Date(selectedEvent.calEventDate).getMonth());
                        } else {
                            // 삭제 실패한 경우
                            alert('Error deleting event: ' + data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Error deleting event');
                    });
            }
        };
    }

    // 일정 추가 모달 열기
    document.querySelector('.fa-calendar-plus').addEventListener('click', function () {
        const addEventModal = document.getElementById('addEventModal');
        addEventModal.style.display = 'block';

        document.getElementById('calEventTitle').value = '';
        document.getElementById('calEventDate').value = '';
        document.getElementById('calEventDescription').value = '';
        document.getElementById('calColorIndex').value = '';

        document.querySelectorAll('.color-picker div').forEach(function (colorDiv) {
            colorDiv.addEventListener('click', function () {
                document.getElementById('calColorIndex').value = this.getAttribute('data-color-index');
                updateTeamColorPreview(document.getElementById('calColorIndex').value); // Update color preview
            });
        });

        const closeBtn = addEventModal.querySelector('.close');
        closeBtn.onclick = function () {
            addEventModal.style.display = 'none';
        };
    });

    // 일정 저장
    document.getElementById('saveEventButton').addEventListener('click', function () {
        const title = document.getElementById('calEventTitle').value || 'Event';
        const date = document.getElementById('calEventDate').value || new Date().toISOString().split('T')[0]; // 날짜만 가져오기
        const description = document.getElementById('calEventDescription').value || 'None';
        const colorIndex = document.getElementById('calColorIndex').value || null;

        // 일정 저장 내용
        const newEvent = {
            calEventId: myTeamCalEvents.length + 1,
            calEventDate: date,
            calEventTitle: title,
            calEventDescription: description,
            calEventCreateAt: new Date().toISOString(),
            colorIndexId: colorIndex, //비동기로 렌더링되지 않는 문제
            userName: nowUserNameFromServer,
            updateBy: "아직 아무도 수정하지 않았어요!",
            departmentId: nowDepartmentIdFromServer,
            teamCalendarId: nowTeamIdFromServer,
        };

        // 클라이언트 로그 추가
        console.log("Event to be saved:", newEvent);

        // AJAX 요청으로 이벤트 저장
        fetch('/api/calendar/addTeamEvent', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newEvent),
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    //새로운 이벤트 배열에 추가하여 렌더링 하기
                    myTeamCalEvents.push(newEvent);
                    //달력에 추가한 화면바로 비동기로 렌더링하기
                    renderTeamCalendar(myTeamCalEvents, new Date(date).getFullYear(), new Date(date).getMonth());
                    document.getElementById('addEventModal').style.display = 'none';
                } else {
                    alert('로그인이 필요한 서비스 입니다. ');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('로그인이 필요한 서비스 입니다.');
            });
    });


    // DOMContentLoaded 이벤트 발생 시 실행
    document.addEventListener('DOMContentLoaded', function () {

        // 이전 달로 넘어가기
        document.getElementById('prev-month').addEventListener('click', function () {
            if (teamCurrentMonth === 0) {
                teamCurrentYear--;
                teamCurrentMonth = 11;
            } else {
                teamCurrentMonth--;
            }
            fetchTeamEvents(teamCurrentYear, teamCurrentMonth, () => renderTeamCalendar(myTeamCalEvents, teamCurrentYear, teamCurrentMonth));
        });

        //다음달로 넘어가기
        document.getElementById('next-month').addEventListener('click', function () {
            if (teamCurrentMonth === 11) {
                teamCurrentYear++;
                teamCurrentMonth = 0;
            } else {
                teamCurrentMonth++;
            }
            fetchTeamEvents(teamCurrentYear, teamCurrentMonth, () => renderTeamCalendar(myTeamCalEvents, teamCurrentYear, teamCurrentMonth));
        });
        fetchTeamEvents(teamCurrentYear, teamCurrentMonth, () => renderTeamCalendar(myTeamCalEvents, teamCurrentYear, teamCurrentMonth));
    });

</script>

<%--현재 날짜 --%>
<%--<div>Formatted Date: <span id="formattedDate"><c:out value="${formattedDate}" /></span></div>--%>
<div class="team-name"># team <span ><c:out value="${departmentName}" /></span> calendar</div>
</body>
</html>