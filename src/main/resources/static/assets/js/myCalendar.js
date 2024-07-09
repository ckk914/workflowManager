// JSON 형식의 문자열을 자바스크립트 객체로 반환하기
const myCalEvents = JSON.parse('<c:out value="${mycalEvents}" escapeXml="false" />'); // 전역변수로 놓고 렌더링
console.log("mycalevents",myCalEvents);


// 일정 추가 모달 열기
document.querySelector('.fa-calendar-plus').addEventListener('click', function () {
    const addEventModal = document.getElementById('addEventModal');
    addEventModal.style.display = 'block';

    // 모달 외부를 클릭하면 닫기
    window.onclick = function (event) {
        if (event.target == addEventModal) {
            addEventModal.style.display = 'none';
        }
    };

    // 닫기 버튼 클릭 시 모달 닫기
    const closeBtn = addEventModal.querySelector('.close');
    closeBtn.onclick = function () {
        addEventModal.style.display = 'none';
    };
});

// 원하는 형광 색상 선택
document.querySelectorAll('.color-picker div').forEach(function (colorDiv) {
    colorDiv.addEventListener('click', function () {
        document.getElementById('calColorIndex').value = this.getAttribute('data-color-index');

        // 모든 colorDiv에서 선택된 스타일 제거
        document.querySelectorAll('.color-picker div').forEach(function (div) {
            div.classList.remove('selected');
        });

        // 선택된 colorDiv에 선택된 스타일 추가
        this.classList.add('selected');
    });
});

document.addEventListener('DOMContentLoaded', function () {

    // 첫 번째 이벤트에서 userId 속성을 가져옴
    const userId = myCalEvents.length > 0 ? myCalEvents[0].userId : "";
    const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    let currentYear = new Date().getFullYear();
    let currentMonth = new Date().getMonth();

    // 초기 데이터 로드
    fetchEvents(currentYear, currentMonth);

    function fetchEvents(year, month) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `/api/calendar/myEvents?year=\${year}&month=\${month + 1}`, true);

        console.log(`/api/calendar/myEvents?year=\${year}&month=\${month + 1}`);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const data = JSON.parse(xhr.responseText);
                    renderCalendar(data, year, month);
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

    //화면에 달력 데이터 렌더링
    function renderCalendar(events, year, month) {
        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();

        let calendarHtml = '<table class="calendar">';
        calendarHtml += '<tr>';
        calendarHtml += '<th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th>';
        calendarHtml += '</tr><tr>';

        for (let i = 0; i < firstDay; i++) {
            calendarHtml += '<td></td>';
        }

        for (let date = 1; date <= lastDate; date++) {
            const day = (firstDay + date - 1) % 7;
            if (day === 0 && date > 1) {
                calendarHtml += '</tr><tr>';
            }

            const yearStr = year.toString();
            const monthStr = (month + 1 < 10 ? '0' + (month + 1) : month + 1).toString();
            const dateStr = (date < 10 ? '0' + date : date).toString();

            const fullDateStr = `\${yearStr}-\${monthStr}-\${dateStr}`;

            calendarHtml += `<td><div>\${date}</div>`;

            // 해당 날짜에 해당하는 이벤트 필터링
            const filteredEvents = events.filter(event => event.calEventDate.startsWith(fullDateStr));

            if (filteredEvents.length > 0) {
                filteredEvents.forEach(event => {
                    const colorClass = `event-\${getColorByIndex(event.colorIndexId)}`;
                    calendarHtml += `<div class="event \${colorClass}" onclick="openModal(\${event.calEventId})">\${event.calEventTitle}</div>`;
                });
            }

            calendarHtml += '</td>';
        }

        calendarHtml += '</tr></table>';

        document.getElementById('calendar').innerHTML = calendarHtml;
        updateCurrentMonth(year, month);
    }
    // 현재 날짜 업데이트 함수 전역
    function updateCurrentMonth(year, month) {
        document.getElementById('current-month').textContent = `\${monthNames[month]} \${year}`;
    }


    // colorindex에 따라 색 부여하기
    function getColorByIndex(index) {
        switch (index) {
            case 1:
                return 'lightblue';
            case 2:
                return 'lightgreen';
            case 3:
                return 'lightcoral';
            case 4:
                return 'lightsalmon';
            case 5:
                return 'lightseagreen';
            default:
                return 'lightgray';
        }
    }

    // 이전 달로 넘어가기
    document.getElementById('prev-month').addEventListener('click', function () {
        if (currentMonth === 0) {
            currentYear--;
            currentMonth = 11;
        } else {
            currentMonth--;
        }
        fetchEvents(currentYear, currentMonth);
    });

    //다음달로 넘어가기
    document.getElementById('next-month').addEventListener('click', function () {
        if (currentMonth === 11) {
            currentYear++;
            currentMonth = 0;
        } else {
            currentMonth++;
        }
        fetchEvents(currentYear, currentMonth);
    });


    // 이벤트 상세보기
    function openModal(eventId) {
        console.log('openModal called with eventId:', eventId);

        const selectedEvent = myCalEvents.find(event => event.calEventId === eventId);
        console.log('Selected Event:', selectedEvent);

        const modal = document.getElementById('eventModal');
        const modalContent = modal.querySelector('.modal-content');
        const eventDetails = modal.querySelector('#eventDetails');

        eventDetails.innerHTML = `
    <li><strong>제목:</strong> \${selectedEvent.calEventTitle}</li>
    <li><strong>이벤트 내용:</strong> \${selectedEvent.calEventDescription}</li>
    <li><strong>이벤트 날짜:</strong> \${selectedEvent.calEventDate}</li>
    <li><strong>작성자:</strong> \${selectedEvent.userName}</li>
  `;
        // <li><strong>색상:</strong> \${getColorByIndex(selectedEvent.colorIndexId)}</li>
        // } else {
        //   eventDetails.innerHTML = '<li>이벤트 정보를 가져올 수 없습니다.</li>';
        // }

        //모달 보이기
        modal.style.display = 'block';

        // 모달 외부를 클릭하면 닫기
        window.onclick = function (event) {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        };

        // 닫기 버튼 클릭 시 모달 닫기
        const closeBtn = modal.querySelector('.close');
        closeBtn.onclick = function () {
            modal.style.display = 'none';
        };

        //수정 버튼시 수정 기능
        const editButton = modal.querySelector('#editEvent');
        const saveChangesButton = modal.querySelector('#saveChangesButton');
        // Edit event
        editButton.onclick = function () {
            const titleSpan = document.getElementById('event-title');
            const dateSpan = document.getElementById('event-date');
            const descriptionSpan = document.getElementById('event-description');

            // Populate inputs with current event details
            titleSpan.innerHTML = `<input type="text" id="edit-title" value="\${titleSpan.innerText}">`;
            dateSpan.innerHTML = `<input type="date" id="edit-date" value="\${dateSpan.innerText}">`;
            descriptionSpan.innerHTML = `<input type="text" id="edit-description" value="\${descriptionSpan.innerText}">`;

            saveChangesButton.style.display = 'block';
        };


        // 수정사항 저장하기
        saveChangesButton.onclick = function () {
            const updatedTitle = document.getElementById('edit-title').value;
            const updatedDate = document.getElementById('edit-date').value;
            const updatedDescription = document.getElementById('edit-description').value;

            // Validate inputs
            if (updatedTitle && updatedDate) {
                const updateEvent = {
                    calEventId: selectedEvent.calEventId,
                    calEventTitle: updatedTitle,
                    calEventDate: updatedDate,
                    calEventDescription: updatedDescription,
                    calColorIndex: selectedEvent.calColorIndex
                };

                // Send update request via AJAX
                fetch('/api/calendar/updateEvent', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(updateEvent),
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            // Update local data
                            selectedEvent.calEventTitle = updatedTitle;
                            selectedEvent.calEventDate = updatedDate;
                            selectedEvent.calEventDescription = updatedDescription;

                            // Close modal and re-render calendar
                            modal.style.display = 'none';
                            renderCalendar();
                        } else {
                            alert('저장 에러' + data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('로그인이 필요합니다 !');
                    });
            } else {
                alert('제목과 날짜를 입력하세요.');
            }
        };
        renderCalendar();
    };




    // 일정 저장
    document.getElementById('saveEventButton').addEventListener('click', function () {
        const title = document.getElementById('calEventTitle').value || 'Event';
        const date = document.getElementById('calEventDate').value || new Date().toISOString().split('T')[0]; // 날짜만 가져오기
        const description = document.getElementById('calEventDescription').value || 'None';
        const colorIndex = document.getElementById('calColorIndex').value || null;

        // 일정 저장 내용
        const newEvent = {
            calEventId: myCalEvents.length + 1,
            calEventDate: date,
            calEventTitle: title,
            calEventDescription: description,
            calEventCreateAt: new Date().toISOString(),
            colorIndexId: colorIndex,
            userName: 'User'
        };

        // AJAX 요청으로 이벤트 저장
        fetch('/api/calendar/addEvent', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newEvent),
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    myCalEvents.push(newEvent);
                    renderCalendar(myCalEvents, new Date(date).getFullYear(), new Date(date).getMonth());
                    document.getElementById('addEventModal').style.display = 'none';
                } else {
                    alert('로그인이 필요합니다 !' + data.message);
                }
            })
            .catch(error => {
                console.error('로그인이 필요합니다.:', error);
                alert('로그인이 필요합니다 ! ');
            });
    });
    renderCalendar();
});