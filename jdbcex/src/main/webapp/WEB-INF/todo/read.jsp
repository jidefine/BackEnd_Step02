<%--
  Created by IntelliJ IDEA.
  User: bitcamp1
  Date: 2024-04-11
  Time: 오후 3:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Read</title>
</head>
<body>
    <div>
        <input type="text" name="tno" value="${dto.tno}" redonly>
    </div>
    <div>
        <input type="text" name="title" value="${dto.title}" redonly>
    </div>
    <div>
        <input type="date" name="dueDate" value="${dto.dueDate}">
    </div>
    <div>
        <input type="checkbox" name="finished"
                            ${dto.finished ? "checked": ""} redonly>
    </div>
    <div>
        <a href="/todo/modify?tno=${dto.tno}">Modify/Remove</a>
        <a href="/todo/list">List</a>
    </div>
</body>
</html>
