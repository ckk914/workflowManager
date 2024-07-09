<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <div class="container">
        <div class="content-box">
            <h2>조회리스트 Best5</h2> 
            <table class="traffic-table">
                <thead>
                    <tr>
                        <th>출발일</th>
                        <th>도착일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="traffic" items="${favoriteView}">
                        <tr class="trafficTable-info">
                            <td>${traffic.departure}</td>
                            <td>${traffic.arrival}</td>
                            <td>${traffic.viewCount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>