<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>workwave:login</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
            <link rel="stylesheet" href="/assets/css/login.css" />
            <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap" rel="stylesheet" />
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
                integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w=="
                crossorigin="anonymous" referrerpolicy="no-referrer" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
                crossorigin="anonymous" />

            <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
            <link rel="icon" href="/assets/img/workwave_logo.png" />
        </head>

        <body>
            <div class="stars"></div>
            <div class="login-container">
                <h1 id="login-title">WORKWAVE</h1>
                <br />
                <form action="login" method="post" id="loginForm">
                    <div class="form-group">
                        <input type="text" id="userId" name="userId" placeholder="USER ID" required />
                    </div>
                    <div class="form-group">
                        <input type="password" id="password" name="password" placeholder="PASSWORD" required />
                    </div>
                    <br />
                    <div class="form-group">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault"
                                name="autoLogin" />
                            <label class="form-check-label" for="flexSwitchCheckDefault" id="auto-login">auto
                                login</label>
                        </div>
                    </div>

                    <button type="submit" class="login-button">
                        LOGIN
                    </button>
                </form>
                <div id="error-message" class="error-message"></div>
                <br />
                <div class="forgot-password">
                    <a href="/forgot-password">Forgot Password?</a>
                </div>
                <br />
                <div class="join-button-container">
                    <a href="/join">
                        Sign Up
                    </a>
                </div>

            </div>
            <script>
                const result = "${result}";
                console.log("result " + result);

                if (result === "NO_ACC") {
                    alert("아이디가 존재하지 않습니다.");
                } else if (result === "NO_PW") {
                    alert("비밀번호가 틀렸습니다.");
                }
            </script>
        </body>

        </html>