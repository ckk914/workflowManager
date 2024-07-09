<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />

    <%@ include file="../include/static-head.jsp" %>
    <title>게시물 수정</title>
    <script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
    <link rel="icon" href="/assets/img/workwave_logo.png" />
    <link rel="stylesheet" href="/assets/css/boardUpdate.css" />
  </head>
  <body>
    <%@ include file="../include/header.jsp" %>

    <div class="update-container">
      <form
        action="update?bno=${board.boardId}"
        method="post"
        enctype="multipart/form-data"
      >
        <input type="hidden" name="boardId" value="${board.boardId}" />
        <h1 class="title">${board.boardTitle}</h1>
        <div class="content-input">
          <textarea
            id="newContent"
            name="newContent"
            rows="10"
            cols="30"
            required
          >
${board.boardContent}</textarea
          >
        </div>
        <script>
          CKEDITOR.replace("newContent", {
            height: 350,
            filebrowserUploadUrl: "upload",
            filebrowserUploadMethod: "form",
          });
        </script>
        <button type="submit">수정</button>
      </form>
      <a class="back-link" href="detail/?bno=${board.boardId}">돌아가기</a>
    </div>
  </body>
</html>
