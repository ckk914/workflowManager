<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" ng-app="myTodo">

<head>
    <title>Work-Wave</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="/assets/css/indexHome.css">
    <link rel="icon" href="/assets/img/workwave_logo.png" />


    <script defer type="text/javascript" src="assets/js/traffic/indexWeather.js"></script>
    <link rel="stylesheet" href="assets/css/traffic/indexWeather.css">


    <style>
        .wave-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(36, 39, 98, 0.3); /* 배경색 설정 */
            background-image: linear-gradient(45deg, rgba(0,0,0,0) 25%, rgba(0,0,0,0) 50%, rgba(0,0,0,0.04) 50%, rgba(0,0,0,0.04) 75%, rgba(0,0,0,0.08) 75%, rgba(0,0,0,0.08) 100%), linear-gradient(-45deg, rgba(0,0,0,0) 25%, rgba(0,0,0,0) 50%, rgba(0,0,0,0.04) 50%, rgba(0,0,0,0.04) 75%, rgba(0,0,0,0.08) 75%, rgba(0,0,0,0.08) 100%);
            background-size: 200% 200%; /* 배경 크기 설정 */
            animation: wave-animation 30s infinite linear; /* 애니메이션 적용 */
            z-index: -1; /* 다른 요소 위에 나타나도록 설정 */
        }

        @keyframes wave-animation {
            0% {
                background-position: 0% 50%;
            }
            100% {
                background-position: 100% 50%;
            }
        }
    </style>
</head>
<body>


<div class="wave-background"></div>


<div id="index-weather"></div>


<div class="clock-container">
    <div class="clock" id="clock">00:00</div>
</div>

<%--<div class="right-top">--%>
<%--    <a href="/myCalendar/viewMyEvent" class="right-box team">MY CALENDAR</a></div>--%>

<div class="left-top">
    <a href="/traffic-map" class="right-box">TRAFFIC INFO</a>
</div>

<div class="left-bottom">
    <a href="/board/list" class="right-box">ANONYMOUS BOARD</a>
    <a href="/lunchMateBoard" class="right-box">LUNCH MENU</a>
</div>

<div class="todo-list-contents">
<%-- 개인 투두리스트 --%>
<div ng-controller="myPersonalController" class="personal-todolist-Box app-container d-flex align-items-center justify-content-center flex-column">
    <h3 class="mb-4"> # ${userName}'s Todo</h3>
    <div class="table-responsive">
        <table class="table table-hover table-bordered">
            <thead class="thead-dark">
            <tr>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="personalTask in personalTasks track by $index" ng-class="{'table-success': personalTask.todoStatus === 'true', 'table-light': personalTask.todoStatus === 'false'}">
                <td id="personal-todo" ng-click="editPersonalTask(personalTask)">
                    <span ng-show="!personalTask.editing" ng-class="{'complete': personalTask.todoStatus === 'true'}">{{personalTask.todoContent}}</span>
                    <input ng-show="personalTask.editing" type="text" ng-model="personalTask.todoContent" ng-blur="updatePersonalTask(personalTask)" ng-keypress="handleKeyPressPersonalTask($event, personalTask)">
                </td>
                <td>
                    <input type="checkbox" ng-model="personalTask.todoStatus" ng-true-value="'true'" ng-false-value="'false'" ng-change="updatePersonalTask(personalTask)">
                </td>
                <td>
                    <button class="btn btn-danger btn-sm delete-btn" ng-click="deletePersonalTask($index)">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="input-group mb-3">
            <input ng-model="yourPersonalTask" type="text" class="form-control" placeholder="Enter a task here">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" ng-click="savePersonalTask()">Save</button>
            </div>
        </div>
    </div>
    <div class="right-bottom">
        <a class="toggle-personal toggle-button" id="personalToggleBtn">PERSONAL TODOLIST</a>
    </div>
</div>

<%-- 팀 투두리스트 --%>
<div ng-controller="myTeamController" class="team-todolist-Box app-container d-flex align-items-center justify-content-center flex-column">
    <h3 class="mb-4"> # Team ${departmentName} Todo</h3>
    <div class="table-responsive">
        <table class="table table-hover table-bordered">
            <thead class="thead-dark">
            <tr>

            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="teamTask in teamTasks track by $index" ng-class="{'table-success': teamTask.teamTodoStatus === 'true', 'table-light': teamTask.teamTodoStatus === 'false'}">
                <td  id="team-todo" ng-click="editTeamTask(teamTask)">
                    <span ng-show="!teamTask.editing" ng-class="{'complete': teamTask.teamTodoStatus === 'true'}">{{teamTask.teamTodoContent}}</span>
                    <input ng-show="teamTask.editing" type="text" ng-model="teamTask.teamTodoContent" ng-blur="updateTeamTask(teamTask)" ng-keypress="handleKeyPressTeamTask($event, teamTask)">
                </td>
                <td>
                    <input type="checkbox" ng-model="teamTask.teamTodoStatus" ng-true-value="'true'" ng-false-value="'false'" ng-change="updateTeamTask(teamTask)">
                </td>
                <td>
                    <button class="btn btn-danger btn-sm delete-btn" ng-click="deleteTeamTask($index)">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="input-group mb-3">
            <input ng-model="yourTeamTask" type="text" class="form-control" placeholder="Enter a task here">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" ng-click="saveTeamTask()">Save</button>
            </div>
        </div>
    </div>
    <div class="right-bottom">
    <a class="toggle-team toggle-button" id="teamTodoToggleBtn">TEAM TODOLIST</a>
    </div>
</div>

</div>
<%--투두리스트 콘텐츠 끝--%>

<%-- 로그인 섹션 --%>
<div class="login">
    <c:choose>
        <c:when test="${not empty dlist.departmentName}">
            <div class="user-info">TEAM_${login.departmentName}</div>
        </c:when>
        <c:when test="${not empty login.nickName}">
            <div class="user-info">${login.nickName}님 환영해요!</div>
            <button onclick="location.href='/member/logout'">LOGOUT</button>
        </c:when>
        <c:otherwise>
            <div class="user-info">로그인이 필요한 서비스입니다.</div>
            <button onclick="location.href='/login'">Login</button>
        </c:otherwise>
    </c:choose>
</div>

<%-- 달력 컨테이너 jsp include--%>
<%--<div class="calendar-at-home">--%>
<%--    <div class="calendar-at-modal" id="personalCalendar">--%>
<%--        <%@page import="java.util.List"%>--%>
<%--        <%@ include file="./schedule/myCalendar.jsp" %>--%>
<%--        <button class="toggle-btn" id="perToggleBtn"><i class="fas fa-chevron-up"></i></button>--%>
<%--    </div>--%>
<%--    <div class="calendar-icon" id="perCalendarIcon">--%>
<%--        MY--%>
<%--        <i class="fas fa-calendar-alt"></i>--%>
<%--    </div>--%>
<%--</div>--%>

<%-- 팀 달력 컨테이너 jsp include--%>
<div class="calendar-at-home">
    <div class="calendar-at-modal" id="teamCalendar">
        <%@page import="java.util.List"%>
        <%@ include file="./schedule/teamCalendar.jsp" %>
        <button class="toggle-btn" id="teamToggleBtn"><i class="fas fa-chevron-up"></i></button>
    </div>
    <div class="calendar-icon" id="teamCalendarIcon">
        <i class="fas fa-calendar-alt"></i>
    </div>
</div>

<%-- 모달 영역: 로그인 필요 --%>
<div id="loginModal" class="modal fade" tagit adbindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">로그인이 필요합니다</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                로그인 후 이용해 주세요.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="location.href='/login'">로그인</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.8.2/angular.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    var app = angular.module('myTodo', []);

    app.controller('myPersonalController', ['$scope', '$http', function($scope, $http) {
        $scope.personalTasks = [];
        $scope.yourPersonalTask = '';

        // 초기화 함수
        function initPersonal() {
            $http.get('/api/todos/user/info')
                .then(function(response) {
                    var userInfo = response.data;
                    $scope.userId = userInfo.userId;
                    fetchPersonalTasks();
                })
                .catch(function(error) {
                    console.error('Error fetching user info:', error);
                });
        }

        // 개인 투두리스트 가져오기
        function fetchPersonalTasks() {
            $http.get('/api/todos/personal')
                .then(function(response) {
                    $scope.personalTasks = response.data;
                })
                .catch(function(error) {
                    console.error('Error fetching personal tasks:', error);
                });
        }

        // 초기화 함수 호출
        initPersonal();

        $scope.savePersonalTask = function() {
            if ($scope.yourPersonalTask.trim() !== '') {
                var newPersonalTask = {
                    todoContent: $scope.yourPersonalTask,
                    todoStatus: 'false', // 문자열로 받아야 함
                    userId: $scope.userId
                };

                $http.post('/api/todos/personal', newPersonalTask)
                    .then(function (response) {
                        var savedMyTask = response.data;
                        if (savedMyTask && savedMyTask.todoId) {
                            $scope.personalTasks.push(savedMyTask);
                        } else {
                            newPersonalTask.todoId = 'temporary_' + (new Date()).getTime();
                            $scope.personalTasks.push(newPersonalTask);
                        }
                        $scope.yourPersonalTask = ''; // 입력 필드 초기화
                    })
                    .catch(function (error) {
                        console.error('Error saving personal task:', error);
                    });
                initPersonal();
            };
        };
        //수정 상태로 전환하는 함수
        $scope.editPersonalTask = function(personalTask) {
            personalTask.editing = true;
        };

        // 업데이트 함수
        $scope.updatePersonalTask = function(personalTask) {
            if (!personalTask.todoId) return; // 아이디 없을 때 업데이트 방지

            var updatedMyTask = angular.copy(personalTask);
            updatedMyTask.todoStatus = personalTask.todoStatus.toString();

            $http.put('/api/todos/personal/' + personalTask.todoId, updatedMyTask)
                .then(function(response) {
                    personalTask.editing = false; // 수정 상태 초기화
                })
                .catch(function(error) {
                    console.error('Error updating personal task:', error);
                });
        };

        // enter 키를 누르면 업데이트 수행
        $scope.handleKeyPressPersonalTask = function(event, personalTask) {
            if (event.keyCode === 13) { // Enter 키 코드는 13입니다.
                $scope.updatePersonalTask(personalTask);
            }
        };

        $scope.deletePersonalTask = function(index) {
            var personalTodoId = $scope.personalTasks[index].todoId;

            $http.delete('/api/todos/personal/' + personalTodoId)
                .then(function(response) {
                    $scope.personalTasks.splice(index, 1); // 배열에서 삭제
                })
                .catch(function(error) {
                    console.error('Error deleting personal task:', error);
                });
        };
    }]);


    app.controller('myTeamController', ['$scope', '$http', function($scope, $http) {
        $scope.teamTasks = [];
        $scope.yourTeamTask = '';

        // 초기화 함수: 서버에서 팀 정보 및 투두리스트 가져오기
        function initTeam() {
            $http.get('/api/todos/user/info')
                .then(function(response) {
                    var userInfo = response.data;
                    $scope.userId = userInfo.userId;
                    $scope.departmentId = userInfo.departmentId;
                    fetchTeamTasks(); // 투두리스트 가져오기 호출
                })
                .catch(function(error) {
                    console.error('Error fetching team info:', error);
                });
        }

        // 팀 투두리스트 가져오기
        function fetchTeamTasks() {
            $http.get('/api/todos/team/aTeamTodos')
                .then(function(response) {
                    $scope.teamTasks = response.data;
                })
                .catch(function(error) {
                    console.error('Error fetching team tasks:', error);
                });
        }

        // 페이지 로딩 시 초기화 함수 호출
        initTeam();
        $scope.saveTeamTask = function() {
            if ($scope.yourTeamTask.trim() !== '') {
                var newTeamTask = {
                    teamTodoContent: $scope.yourTeamTask,
                    teamTodoStatus: 'false', // 기본값을 문자열 'false'로 설정
                    departmentId: $scope.departmentId,
                    userId: $scope.userId
                };
            }
            $http.post('/api/todos/team/aTeamTodos', newTeamTask)
                .then(function(response) {
                    var savedTask = response.data;
                    if (savedTask && savedTask.teamTodoId) {
                        $scope.teamTasks.push(savedTask); // 서버에서 반환된 데이터를 배열에 추가
                    } else {
                        newTeamTask.teamTodoId = 'temporary_' + (new Date()).getTime();
                        $scope.teamTasks.push(newTeamTask); // 임시로 추가
                    }
                    $scope.yourTeamTask = ''; // 입력 필드 초기화
                })
                .catch(function(error) {
                    console.error('팀 투두를 저장하는 중 오류 발생:', error);
                });
            fetchTeamTasks();
            initTeam();
        };

        //팀 테스크 수정 상태로 전환하는 함수
        $scope.editTeamTask = function(teamTask) {
            teamTask.editing = true;
        };

        // 팀 테스크 수정사항 업데이트 함수
        $scope.updateTeamTask = function(teamTask) {
            if (!teamTask.teamTodoId) return; // teamTodoId가 없으면 업데이트하지 않음

            var updatedTask = angular.copy(teamTask);
            updatedTask.teamTodoStatus = teamTask.teamTodoStatus.toString(); // 상태를 문자열로 변환

            $http.put('/api/todos/team/' + teamTask.teamTodoId, updatedTask)
                .then(function(response) {
                    teamTask.editing = false; // 수정 상태 초기화
                })
                .catch(function(error) {
                    console.error('Error updating team task:', error);
                });
        };

        // Enter 키를 눌렀을 때 업데이트 수행
        $scope.handleKeyPressTeamTask = function(event, teamTask) {

            if (event.keyCode === 13) { // Enter 키 코드는 13
                $scope.updateTeamTask(teamTask);
            }
        };

        // 팀 투두리스트 삭제 함수
        $scope.deleteTeamTask = function(index) {
            var teamTodoId = $scope.teamTasks[index].teamTodoId;

            $http.delete('/api/todos/team/' + teamTodoId)
                .then(function(response) {
                    $scope.teamTasks.splice(index, 1); // 배열에서 삭제
                })
                .catch(function(error) {
                    console.error('Error deleting team task:', error);
                });
            fetchTeamTasks();
            initTeam();
        };
    }]);

    $(document).ready(function() {
        // 페이지 로드 시 기본적으로 내용을 감추기
        $('.personal-todolist-Box').addClass('minimized');
        $('.personal-todolist-Box .table-responsive').hide();

        $('.team-todolist-Box').addClass('minimized');
        $('.team-todolist-Box .table-responsive').hide();

        $('#teamCalendar').addClass('minimized');
        $('#teamCalendarIcon').show();
        $('#teamCalendar').hide();

        // 개인 투두리스트 토글 버튼 클릭 시
        $('#personalToggleBtn').click(function() {
            $('.personal-todolist-Box').toggleClass('minimized');
            if ($('.personal-todolist-Box').hasClass('minimized')) {
                $('.personal-todolist-Box .table-responsive').hide(); // 개인 투두리스트 내용 감추기
            } else {
                $('.personal-todolist-Box .table-responsive').show(); // 개인 투두리스트 내용 표시
            }
        });

        // 팀 투두리스트 토글 버튼 클릭 시
        $('#teamTodoToggleBtn').click(function() {
            $('.team-todolist-Box').toggleClass('minimized');
            if ($('.team-todolist-Box').hasClass('minimized')) {
                $('.team-todolist-Box .table-responsive').hide(); // 팀 투두리스트 내용 감추기
            } else {
                $('.team-todolist-Box .table-responsive').show(); // 팀 투두리스트 내용 표시
            }
        });

        // 팀 달력 토글 버튼 클릭 시
        $('#teamToggleBtn').click(function() {
            $('#teamCalendar').toggleClass('minimized');
            if ($('#teamCalendar').hasClass('minimized')) {
                $('#teamCalendarIcon').show();
                $('#teamCalendar').hide(); // 팀 달력 감추기
            } else {
                $('#teamCalendarIcon').hide();
                $('#teamCalendar').show(); // 팀 달력 표시
            }
        });

        // 팀 달력 아이콘 클릭 시 달력 표시
        $('#teamCalendarIcon').click(function() {
            $('#teamCalendar').removeClass('minimized');
            $('#teamCalendarIcon').hide();
            $('#teamCalendar').show();
        });
    });

    // $(document).ready(function() {
    //     // 개인 투두리스트 토글 버튼 클릭 시
    //     $('#personalToggleBtn').click(function() {
    //         $('.personal-todolist-Box').toggleClass('minimized');
    //         if ($('.personal-todolist-Box').hasClass('minimized')) {
    //             $('.personal-todolist-Box .table-responsive').hide(); // 개인 투두리스트 내용 감추기
    //         } else {
    //             $('.personal-todolist-Box .table-responsive').show(); // 개인 투두리스트 내용 표시
    //         }
    //     });
    //
    //     // 팀 투두리스트 토글 버튼 클릭 시
    //     $('#teamTodoToggleBtn').click(function() {
    //         $('.team-todolist-Box').toggleClass('minimized');
    //         if ($('.team-todolist-Box').hasClass('minimized')) {
    //             $('.team-todolist-Box .table-responsive').hide(); // 팀 투두리스트 내용 감추기
    //         } else {
    //             $('.team-todolist-Box .table-responsive').show(); // 팀 투두리스트 내용 표시
    //         }
    //     });
    //
    //     // 팀 달력 토글 버튼 클릭 시
    //     $('#teamToggleBtn').click(function() {
    //         $('#teamCalendar').toggleClass('minimized');
    //         if ($('#teamCalendar').hasClass('minimized')) {
    //             $('#teamCalendarIcon').show();
    //             $('#teamCalendar').hide(); // 팀 달력 감추기
    //         } else {
    //             $('#teamCalendarIcon').hide();
    //             $('#teamCalendar').show(); // 팀 달력 표시
    //         }
    //     });
    //
    //     // 팀 달력 아이콘 클릭 시 달력 표시
    //     $('#teamCalendarIcon').click(function() {
    //         $('#teamCalendar').removeClass('minimized');
    //         $('#teamCalendarIcon').hide();
    //         $('#teamCalendar').show();
    //     });
    // });

    function updateClock() {
        var now = new Date();
        var hours = String(now.getHours()).padStart(2, '0');
        var minutes = String(now.getMinutes()).padStart(2, '0');
        var timeString = hours + ':' + minutes;
        document.getElementById('clock').textContent = timeString;
    }

    setInterval(updateClock, 1000);
    updateClock();

</script>
</body>
</html>

