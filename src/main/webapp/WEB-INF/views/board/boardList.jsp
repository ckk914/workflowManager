<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <%@ include file="../include/static-head.jsp" %>
    <link rel="icon" href="/assets/img/workwave_logo.png" />
    <link rel="stylesheet" href="/assets/css/boardList.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
      integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <title>게시판</title>
  </head>
  <body>
    <%@ include file="../include/header.jsp" %>

    <div class="board-main-content">
      <div class="container">
        <form class="search-bar" method="get" action="/board/list">
          <select class="search-type" name="type">
            <option value="tc" <c:if test="${param.type == 'tc'}">selected</c:if>>제목+내용</option>
            <option value="view" <c:if test="${param.type == 'view'}">selected</c:if>>조회수</option>
            <option value="likes" <c:if test="${param.type == 'likes'}">selected</c:if>>좋아요 수</option>
            <option value="id" <c:if test="${param.type == 'id'}">selected</c:if>>내가 쓴 글</option>
          </select>
          <input
            class="search-input"
            type="text"
            name="keyword"
            placeholder="검색어 입력"
            value="${param.keyword}"
          />
          <button class="search-btn" type="submit">
            <i class="fas fa-search"></i>
          </button>
        </form>

        <button class="new-board" onclick="location.href='/board/write'">
          글 쓰기
        </button>

        <table class="board-table">
          <c:forEach var="board" items="${boards}">
            <tr class="one-board">
              <td class="board-info">
                <a class="board-link" href="/board/detail?bno=${board.boardId}"
                  >${board.boardTitle}
                </a>
                <div class="nickname">
                  <i class="fas fa-user-secret"></i>${board.boardNickname}
                </div>
                <div class="board-icon">
                  <i class="fas fa-eye"></i
                  ><span class="view">${board.viewCount}</span>
                  <i class="far fa-comment"></i
                  ><span class="reply-count">${board.replyCount}</span>
                  <i class="far fa-thumbs-up"></i
                  ><span class="likes">${board.likes}</span>
                  <i class="far fa-thumbs-down"></i
                  ><span class="dislikes">${board.dislikes}</span>
                  <span class="created-at"
                    >${board.formattedBoardCreatedAt}</span
                  >
                </div>
              </td>
              <td class="board-image" style="line-height: 0">
                <c:choose>
                  <c:when test="${empty board.boardContent}"> </c:when>
                  <c:otherwise> ${board.boardContent} </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </table>

        <div class="bottom-section">
          <nav aria-label="Page navigation example">
            <ul class="pagination pagination-lg pagination-custom">
              <c:if test="${maker.pageInfo.pageNo != 1}">
                <li class="page-item">
                  <a
                    class="page-link"
                    href="/board/list?pageNo=1&type=${s.type}&keyword=${s.keyword}"
                    >&lt;&lt;</a
                  >
                </li>
              </c:if>

              <c:if test="${maker.prev}">
                <li class="page-item">
                  <a
                    class="page-link"
                    href="/board/list?pageNo=${maker.begin - 1}&type=${s.type}&keyword=${s.keyword}"
                    >&lt;</a
                  >
                </li>
              </c:if>

              <c:forEach var="i" begin="${maker.begin}" end="${maker.end}">
                <li data-page-num="${i}" class="page-item">
                  <a
                    class="page-link ${i == currentPage ? 'active' : ''}"
                    href="/board/list?pageNo=${i}&type=${s.type}&keyword=${s.keyword}"
                    >${i}</a
                  >
                </li>
              </c:forEach>

              <c:if test="${maker.next}">
                <li class="page-item">
                  <a
                    class="page-link"
                    href="/board/list?pageNo=${maker.end + 1}&type=${s.type}&keyword=${s.keyword}"
                    >&gt;</a
                  >
                </li>
              </c:if>

              <c:if test="${maker.pageInfo.pageNo != maker.finalPage}">
                <li class="page-item">
                  <a
                    class="page-link"
                    href="/board/list?pageNo=${maker.finalPage}&type=${s.type}&keyword=${s.keyword}"
                    >&gt;&gt;</a
                  >
                </li>
              </c:if>
            </ul>
          </nav>
        </div>
      </div>
    </div>

    <script>
      document.addEventListener("DOMContentLoaded", function () {
        let params = new URLSearchParams(window.location.search);
        let currentPage = params.get("pageNo") || 1;
        let pageLinks = document.querySelectorAll(".pagination a");
        pageLinks.forEach(function (link) {
          if (parseInt(link.textContent) == currentPage) {
            link.classList.add("active");
          }
        });
      });
    </script>
  </body>
</html>
