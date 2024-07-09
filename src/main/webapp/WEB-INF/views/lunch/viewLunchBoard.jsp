<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Lunch Mate Post</title>
</head>
<body>
<h1>Lunch Mate Post Details</h1>
<p><strong>User ID:</strong> ${board.userId}</p>
<p><strong>Title:</strong> ${board.lunchPostTitle}</p>
<p><strong>Date:</strong> ${board.lunchDate}</p>
<p><strong>Location:</strong> ${board.lunchLocation}</p>
<p><strong>Menu Name:</strong> ${board.lunchMenuName}</p>
<p><strong>Max Attendees:</strong> ${board.lunchAttendees}</p>
<p><strong>Status:</strong> ${board.progressStatus}</p>
<a href="/lunchMateBoard">Back to List</a>
</body>
</html>
