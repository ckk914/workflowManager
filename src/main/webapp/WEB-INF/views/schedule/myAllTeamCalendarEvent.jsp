<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Calendar Events</title>
</head>
<body>
<h1>my TEAM Calendar Events</h1>
<table border="1">
  <tr>
    <th>TEAM Event ID</th>
    <th>TEAM Event Date</th>
    <th>TEAM Event Title</th>
    <th>TEAM Event Description</th>
    <th>TEAM create At</th>
    <th>TEAM update At</th>
    <th>TEAM Color Index ID</th>
    <th>TEAM notice ID</th>
    <th>TEAM calendar ID</th>
    <th>TEAM User name</th>
    <th>TEAM name</th>
  </tr>
  <c:forEach var="event" items="${calendarEvents}">
    <tr>
      <td>${event.calEventId}</td>
      <td>${event.calEventDate}</td>
      <td>${event.calEventTitle}</td>
      <td>${event.calEventDescription}</td>
      <td>${event.calEventCreateAt}</td>
      <td>${event.calEventUpdateAt}</td>
      <td>${event.colorIndexId}</td>
      <td>${event.noticeId}</td>
      <td>${event.teamCalendarId}</td>
      <td>${event.userName}</td>
      <td>${event.departmentName}</td>

    </tr>
  </c:forEach>
</table>
</body>
</html>
