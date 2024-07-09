<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>비밀번호 확인</title>
    <%@ include file="../include/static-head.jsp" %>
    <link rel="icon" href="/assets/img/workwave_logo.png" />
    <link rel="stylesheet" href="/assets/css/boardPwCheck.css" />

  </head>
  <body>
    <%@ include file="../include/header.jsp" %> <% String bno =
    request.getParameter("bno"); String action = request.getParameter("action");
    %>
    <div class="pw-content">
      <h2>비밀번호 확인</h2>
      <form action="pwcheck?bno=<%= bno %>&action=<%= action %>" method="post">
        <div class="form-group">
          <!-- <label for="boardPassword"></label> -->
          <input
            type="password"
            id="boardPassword"
            name="boardPassword"
            placeholder="비밀번호를 입력하세요"
            required
          />
        </div>
        <div class="form-btn-group">
          <button type="submit">확인</button>
          <button
            type="button"
            class="cancel-button"
            onclick="window.location.href='http://172.30.1.73:8383/board/detail?bno=<%= bno %>'"
          >
            취소
          </button>
        </div>
      </form>
    </div>

    <script type="text/javascript">
      window.onload = function () {
        let errorMessage = "${error}";
        if (errorMessage) {
          alert(errorMessage);
        }
      };
    </script>
  </body>
</html>
