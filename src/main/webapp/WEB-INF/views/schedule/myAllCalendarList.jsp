<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head var="calendar" items="${calendars}">
    <title>${calendar.userName} Calendars</title>
</head>
<body>
<h1>Calendars</h1>
<table border="1">
    <tr>
        <th>Calendar ID</th>
        <th>Calendar Name</th>
        <th>Team Calendar ID</th>
        <th>Team Calendar Name</th>
        <th>User ID</th>
    </tr>
    <c:forEach var="calendar" items="${calendars}">

        <h1>${calendar.userName}</h1>
        <tr>
            <td>${calendar.calendarId}</td>
            <td>${calendar.calendarName}</td>
            <td>${calendar.teamCalendarId}</td>
            <td>${calendar.teamCalendarName}</td>
            <td>${calendar.userId}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>


