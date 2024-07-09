<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>새 글 쓰기</title>
    </head>

    <body>
        <h1>새 글 쓰기</h1>
        <form action="/lunchMateBoard/new" method="post">
            <!-- 작성자 정보는 자동으로 설정되도록 변경 -->
            <label for="userId">작성자:</label>
           <span id="userId"><%= session.getAttribute("loggedInUser").getUserId() %></span><br>

            <label for="lunchPostTitle">제목:</label>
            <input type="text" id="lunchPostTitle" name="lunchPostTitle" required><br>

            <label for="eatTime">식사 일정:</label>
            <input type="date" id="eatTime" name="eatTime" required><br>

            <label for="lunchLocation">식당 위치:</label>
            <input type="text" id="lunchLocation" name="lunchLocation" required><br>

            <label for="lunchMenuName">메뉴 이름:</label>
            <input type="text" id="lunchMenuName" name="lunchMenuName" required><br>

            <label for="lunchAttendees">인원:</label>
            <input type="number" id="lunchAttendees" name="lunchAttendees" required><br>

            <!-- <label for="progressStatus">상태:</label>
            <input type="text" id="progressStatus" name="progressStatus"><br> -->

            <button type="submit">등록</button>
        </form>
    </body>

    </html>