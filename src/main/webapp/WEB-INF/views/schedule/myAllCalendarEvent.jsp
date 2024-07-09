<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Calendar Events</title>
</head>
<body>
<h1>Calendar Events</h1>
<table border="1">
  <tr>
    <th>Event ID</th>
    <th>Event Date</th>
    <th>Event Title</th>
    <th>Event Description</th>
    <th>create At</th>
    <th>update At</th>
    <th>Color Index ID</th>
    <th>User ID</th>
    <th>User name</th>
    <th>Calendar ID</th>
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
      <td>${event.userId}</td>
      <td>${event.userName}</td>
      <td>${event.calendarId}</td>

    </tr>
  </c:forEach>
</table>
</body>
</html>
