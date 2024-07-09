<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/static-head.jsp" %>
    <%@ include file="../include/header.jsp" %>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <script defer type="text/javascript" src="assets/js/traffic/myTrafficInfo.js"></script>
    <link rel="stylesheet" href="assets/css/traffic/myTrafficInfo.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
</head>
<body>
    <div class="form-container">
        <c:choose>
            <c:when test="${empty totalTraffic}">
                <div>
                    <h2>데이터가 없습니다.</h2>
                    <!-- 다른 내용 추가 가능 -->
                </div>
            </c:when>
            <c:otherwise>
                <c:set var="isFirst" value="true" />
                <c:forEach var="traffic" items="${totalTraffic}">
                    <c:if test="${isFirst}">
                        <div class="header-container">
                            <h1>${traffic.userId} </h1><h3>님의 이용내역</h3>
                            <a href="traffic-map">
                                <i class="fas fa-undo-alt"></i>
                            </a>
                        </div>
                        <div class="sort-container">
                            <select class="form-select" name="sort" id="sort" form="sortForm">
                                <option value="departure">출발역</option>
                                <option value="regDate">등록일</option>
                            </select>
                            <button type="submit" form="sortForm">정렬</button>
                        </div>
                        <a class="favorite-list" href="/traffic-rank">
                            <i id="click-favorite" class="far fa-star"></i>
                        </a>
                        <form id="sortForm" action="/traffic-myInfo" method="get"></form>
                        <c:set var="isFirst" value="false" />
                    </c:if>
                    <div class="regDate-container">
                        <div class="regDate" data-reg-date="${traffic.regDateTime}">
                            ${traffic.regDateTime}
                        </div>
                    </div>
                    <div class="traffic-info">
                        <div class="departure-location">
                            <span>${traffic.departure}</span>
                            <i class="fas fa-subway"></i>
                        </div>
                        <div>
                            <i class="fas fa-long-arrow-alt-right"></i>
                        </div>
                        <div class="arrival-location">
                            <span>${traffic.arrival}</span>
                            <i class="fas fa-subway"></i>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <ul class="pagination">
      <c:if test="${maker.pageInfo.pageNo != 1}">
        <li class="page-item">
          <a class="page-link" href="/traffic-myInfo?pageNo=1&type=${s.type}&keyword=${s.keyword}">&lt;&lt;</a>
        </li>
      </c:if>

      <c:if test="${maker.prev}">
        <li class="page-item">
          <a class="page-link"
            href="/traffic-myInfo?pageNo=${maker.begin - 1}&type=${s.type}&keyword=${s.keyword}">prev</a>
        </li>
      </c:if>


        <c:forEach var="i" begin="${maker.begin}" end="${maker.end}">
            <li data-page-num="${i}">
                <a href="/traffic-myInfo?pageNo=${i}">${i}</a>
            </li>
        </c:forEach>

        <c:if test="${maker.next}">
          <li class="page-item">
            <a class="page-link"
              href="/traffic-myInfo?pageNo=${maker.end + 1}&type=${s.type}&keyword=${s.keyword}">next</a>
          </li>
        </c:if>

        <c:if test="${maker.pageInfo.pageNo != maker.finalPage}">
          <li class="page-item">
            <a class="page-link"
              href="/traffic-myInfo?pageNo=${maker.finalPage}&type=${s.type}&keyword=${s.keyword}">&gt;&gt;</a>
          </li>
        </c:if>
    </ul>
</body>
</html>