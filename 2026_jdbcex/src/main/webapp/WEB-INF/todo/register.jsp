<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ugo20
  Date: 2026-08-03
  Time: 오후 10:55
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/todo/register" method="post">
    <div>
        <input type="text" name="title" placeholder="INSERT TITLE">
    </div>
    <div>
        <input type="date" name="dueDate">
    </div>
    <div>
        <button type="reset">Reset</button>
        <button type="submit">Register</button>
    </div>
</form>
</body>
</html>
