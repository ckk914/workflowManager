<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>500 - 페이지를 찾을 수 없습니다</title>
  <style>
    .wave-background {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(20, 22, 64, 0.8); /* 배경색 설정 */
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

    body {
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      background-color: #f0f8ff;
      background-image: url('/assets/img/404errorpage.png');
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
      font-family: 'Arial', sans-serif;
      color: #333;
      text-align: center;
    }

    .container {
      padding: 2em;
      border-radius: 10px;
      background: rgba(237, 240, 243, 0.66);
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    h1 {
      color: #F2EFEB;
      font-size: 2.5em;
      margin-bottom: 0.5em;
    }

    p {
      color: #F2EFEB;
      font-size: 1.2em;
      margin-bottom: 1.5em;
    }

    a {
      text-decoration: none;
      color: #F2EFEB;
      background: #515373;
      padding: 0.75em 1.5em;
      border-radius: 25px;
      font-size: 1.2em;
      transition: background 0.3s ease;
    }

    a:hover {
      background: #404259;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="error-image"></div>
  <h1>500! 페이지를 찾을 수 없습니다.</h1>
  <p>요청하신 페이지가 존재하지 않거나, 이동되었을 수 있습니다.</p>
  <a href="/">홈으로 돌아가기</a>
</div>
</body>
</html>