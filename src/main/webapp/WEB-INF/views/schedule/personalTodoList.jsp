
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" ng-app="myAppPersonal">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Todo List</title>
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
<body ng-controller="myPersonalController" class="app-container d-flex align-items-center justify-content-center flex-column">

<h3 class="mb-4">Personal Todo List</h3>

<div class="input-group mb-3">
    <input ng-model="yourPersonalTask" type="text" class="form-control" placeholder="Enter a task here">
    <div class="input-group-append">
        <button class="btn btn-primary" type="button" ng-click="savePersonalTask()">Save</button>
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
        <tr ng-repeat="personalTask in personalTasks track by $index" ng-class="{'table-success': personalTask.todoStatus === 'true', 'table-light': personalTask.todoStatus === 'false'}">
            <td>{{$index + 1}}</td>
            <td ng-click="editPersonalTask(personalTask)">
                <!-- 동그라미 표시 -->
<%--                <span class="color-dot" ng-style="{'background-color': personalColorIndexMap[personalTask.colorIndexId]}"></span>--%>
                <span ng-show="!personalTask.editing" ng-class="{'complete': personalTask.todoStatus === 'true'}">{{personalTask.todoContent}}</span>
                <input ng-show="personalTask.editing" type="text" ng-model="personalTask.todoContent" ng-blur="updatePersonalTask(personalTask)" ng-keypress="handleKeyPressPersonalTask($event, personalTask)">
            </td>
            <td>
                <input type="checkbox" ng-model="personalTask.todoStatus" ng-true-value="'true'" ng-false-value="'false'" ng-change="updatePersonalTask(personalTask)">
            </td>
            <td>
                <button class="btn btn-danger btn-sm" ng-click="deletePersonalTask($index)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.9/angular.min.js"></script>
<script>
    var appPersonal = angular.module("myAppPersonal", []);

    appPersonal.controller("myPersonalController", function($scope, $http) {
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



        // 투두리스트 저장 함수
        $scope.savePersonalTask = function() {
            var newPersonalTask = {
                todoContent: $scope.yourPersonalTask,
                todoStatus: 'false', // 문자열로 받아야 함
                userId: $scope.userId
            };

            $http.post('/api/todos/personal', newPersonalTask)
                .then(function(response) {
                    var savedMyTask = response.data;
                    if (savedMyTask && savedMyTask.todoId) {
                        $scope.personalTasks.push(savedMyTask);
                    } else {
                        newPersonalTask.todoId = 'temporary_' + (new Date()).getTime();
                        $scope.personalTasks.push(newPersonalTask);
                    }
                    $scope.yourPersonalTask = ''; // 입력 필드 초기화
                })
                .catch(function(error) {
                    console.error('Error saving personal task:', error);
                });
            initPersonal();
        };

        //투두리스트 삭제
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

        // 수정 상태로 전환하는 함수
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

        // Enter 키를 눌렀을 때 업데이트 수행
        $scope.handleKeyPressPersonalTask = function(event, personalTask) {
            if (event.keyCode === 13) { // Enter 키 코드는 13입니다.
                $scope.updatePersonalTask(personalTask);
            }
        };
    });
</script>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>


