<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" ng-app="myAppTeam">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Team Todo List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <style>
        .app-container {
            height: 100vh;
            width: 100%;
        }
        .complete {
            text-decoration: line-through;
            color: gray;
        }
        .color-dot {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            margin-right: 5px;
        }
    </style>
</head>
<body ng-controller="myTeamController" class="app-container d-flex align-items-center justify-content-center flex-column">

<h3 class="mb-4">Team Todo List</h3>

<div class="input-group mb-3">
    <input ng-model="yourTeamTask" type="text" class="form-control" placeholder="Enter a task here">
    <div class="input-group-append">
        <button class="btn btn-primary" type="button" ng-click="saveTeamTask()">Save</button>
    </div>
</div>

<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">No.</th>
            <th scope="col">Todo item</th>
            <th scope="col">Status</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="teamTask in teamTasks track by $index" ng-class="{'table-success': teamTask.teamTodoStatus === 'true', 'table-light': teamTask.teamTodoStatus === 'false'}">
            <td>{{$index + 1}}</td>
            <td ng-click="editTeamTask(teamTask)">
                <span ng-show="!teamTask.editing" ng-class="{'complete': teamTask.teamTodoStatus === 'true'}">{{teamTask.teamTodoContent}}</span>
                <input ng-show="teamTask.editing" type="text" ng-model="teamTask.teamTodoContent" ng-blur="updateTeamTask(teamTask)" ng-keypress="handleKeyPressTeamTask($event, teamTask)">
            </td>
            <td>
                <input type="checkbox" ng-model="teamTask.teamTodoStatus" ng-true-value="'true'" ng-false-value="'false'" ng-change="updateTeamTask(teamTask)">
            </td>
            <td>
                <button class="btn btn-danger btn-sm" ng-click="deleteTeamTask($index)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>
    <%--var sessionUserId = '<%= session.getAttribute("userId") %>';--%>
</script><script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.9/angular.min.js"></script>
<script>
    var appTeam = angular.module("myAppTeam", []);

    appTeam.controller("myTeamController", function($scope, $http) {

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

        // 투두리스트 저장 함수
        $scope.saveTeamTask = function() {
            var newTeamTask = {
                teamTodoContent: $scope.yourTeamTask,
                teamTodoStatus: 'false', // 기본값을 문자열 'false'로 설정
                departmentId: $scope.departmentId,
                userId: $scope.userId
            };

            console.log('Saving Team Task:', newTeamTask); // 로그 추가

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

        // 투두리스트 삭제 함수
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

        // 수정 상태로 전환하는 함수
        $scope.editTeamTask = function(teamTask) {
            teamTask.editing = true;
        };

        // 업데이트 함수
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
    });
</script>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>
