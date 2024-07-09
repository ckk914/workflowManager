

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        
    <!DOCTYPE html>
        <html>

<head>



        <body>
        <head>

            <title>workwave</title>


            <title>Work-Wave</title>

            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
            <link rel="stylesheet" href="<c:url value='../assets/css/main.css' />">

            <%@ include file="include/static-head.jsp" %>

                <style>
                    body {
                        font-family: 'Spoqa Han Sans Neo', sans-serif;
                        margin: 0;
                        padding: 0;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        height: 100vh;
                        background-color: #f5f5f5;
                    }

                    .clock {
                        font-size: 4rem;
                        font-weight: bold;
                        color: #333;
                        margin-left: 80px;
                        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
                    }

                    .right-boxes {
                        display: grid;
                        grid-template-columns: repeat(3, 1fr);
                        grid-gap: 20px;
                        margin-right: 80px;
                    }

                    .right-box {
                        width: 120px;
                        height: 120px;
                        background-color: #fff;
                        border-radius: 10px;
                        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        font-size: 1.4rem;
                        font-weight: bold;
                        color: #333;
                        transition: transform 0.3s ease;
                    }

                    .right-box:hover {
                        transform: translateY(-5px);
                    }

                    .login-container {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        background-color: #f0f0f0;
                        margin: auto;
                    }

                    .login-box {
                        background-color: white;
                        padding: 40px;
                        border-radius: 10px;
                        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                        max-width: 400px;
                        width: 100%;
                        margin-left: 50px;
                        margin-right: 50px;
                    }

                    .login-title {
                        text-align: center;
                        margin-bottom: 30px;
                        color: #333;
                    }

                    .form-group {
                        margin-bottom: 20px;
                    }

                    label {
                        display: block;
                        font-weight: bold;
                        margin-bottom: 5px;
                        color: #555;
                    }

                    input[type="text"],
                    input[type="password"] {
                        width: 100%;
                        padding: 10px;
                        border: 1px solid #ccc;
                        border-radius: 5px;
                        font-size: 16px;
                    }

                    .auto-login {
                        display: flex;
                        align-items: center;
                        font-size: 14px;
                        color: #555;
                    }

                    .auto-login input[type="checkbox"] {
                        margin-right: 5px;
                    }

                    .login-button {
                        display: block;
                        width: 100%;
                        padding: 12px 0;
                        background-color: #007bff;
                        color: white;
                        border: none;
                        border-radius: 5px;
                        font-size: 16px;
                        cursor: pointer;
                    }

                    .join-button {
                        display: block;
                        text-align: center;
                        color: #007bff;
                        text-decoration: none;
                        font-size: 14px;
                    }

                    .error-message {
                        color: red;
                        text-align: center;
                        margin-top: 10px;
                    }

                    .intro-text {
                        display: inline;
                    }

                    .logout-button {
                        display: inline;
                        text-align: center;
                        color: #007bff;
                        text-decoration: none;
                        font-size: 14px;
                    }


                    .profile-box {
                        width: 50px;
                        height: 50px;
                        border-radius: 50%;
                        overflow: hidden;
                    }

                    .profile-box img {
                        width: 100%;
                        height: 100%;
                    }
                </style>
                <link rel="icon" href="/assets/img/workwave_logo.png" />


        </head>

        <body>

            <%@ include file="include/header.jsp" %>

                <div class="clock">00:00:00</div>

                <div class="right-boxes">
                    <a href="/board/list">
                        <div class="right-box">우빈1</div>
                    </a>
                    <a href="#">
                        <div class="right-box">우빈2</div>
                    </a>
                    <a href="/myCalendar/viewMyEvent">
                        <div class="right-box">달력</div>
                    </a>
                    <a href="/myCalendar/viewTeamEvent">
                        <div class="right-box">팀 달력</div>
                    </a>
                    <a href="/viewTodo/personal">
                        <div class="right-box">개인 투두리스트</div>

                    </a>
                    <a href="/viewTodo/team">
                        <div class="right-box">팀 투두리스트</div>
                    </a>
                    <a href="#">
                        <div class="right-box">윤종4</div>
                    </a>
                    <a href="/login">
                        <div class="right-box">경곤1</div>
                    </a>
                    <a href="/traffic-map">
                        <div class="right-box">준원1</div>
                    </a>
                    <a href="/lunchMateBoard">
                        <div class="right-box">승연1</div>
                    </a>
                    <a href="#">
                        <div class="right-box">승연2</div>
                    </a>
                    <a href="#">
                        <div class="right-box">Box 11</div>
                    </a>
                    <a href="#">
                        <div class="right-box">Box 12</div>
                    </a>
                    <a href="#">
                        <div class="right-box">Box 13</div>
                    </a>
                    <a href="/test">
                        <div class="right-box">Box 14</div>
                    </a>
                    <a href="#">
                        <div class="right-box">Box 15</div>
                    </a>
                </div>

                <div class="login-box">
                    <c:if test="${not empty login.nickName}">
                        <div class="profile-box">

                            <c:choose>
                                <c:when test="${login != null && login.profile != null}">
                                    <img src="${login.profile}" alt="profile image">
                                </c:when>
                                <c:otherwise>
                                    <img src="/assets/img/anonymous.png" alt="profile image">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div>
                            <h2 class="intro-text">${login.nickName}님 환영합니다. </h2>
                            <a href="/member/logout" class="logout-button"> <button class="logout-button"> 로그아웃
                                </button>
                            </a>
                        </div>
                    </c:if>
                    <c:if test="${empty login.nickName}">
                        <div class="login-container">
                            <div class="login-box">
                                <h1 class="login-title">로그인</h1>
                                <form action="login" method="post" id="loginForm">
                                    <div class="form-group">
                                        <label for="userId">아이디:</label>
                                        <input type="text" id="userId" name="userId" required />
                                    </div>
                                    <div class="form-group">
                                        <label for="password">비밀번호:</label>
                                        <input type="password" id="password" name="password" required />
                                    </div>
                                    <div class="form-group">
                                        <label for="auto-login" class="auto-login">
                                            <input type="checkbox" id="auto-login" name="autoLogin" />
                                            <span> 자동 로그인</span>
                                        </label>
                                    </div>
                                    <button type="submit" class="login-button">
                                        로그인 <i class="fas fa-sign-in-alt"></i></button>
                                </form>
                                <br /><br />
                                <div class="form-group">
                                    <a href="/join" class="join-button">회원가입</a>
                                </div>
                                <div id="error-message" class="error-message"></div>
                            </div>
                        </div>
                        <script>
                            //서버에서 전송된
                            const result = "${result}";
                            console.log("result " + result);

                            if (result === "NO_ACC") {
                                alert("아이디가 존재하지 않습니다.");
                            } else if (result === "NO_PW") {
                                alert("비밀번호가 틀렸습니다.");
                            }
                        </script>
                    </c:if>
                </div>


        </body>

        </html>