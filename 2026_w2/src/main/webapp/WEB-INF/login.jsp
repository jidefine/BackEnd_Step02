<%--
  Created by IntelliJ IDEA.
  User: ugo20
  Date: 2026-08-05
  Time: 오후 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="/login" method="post">
    <input type="text" name="mid">
    <input type="text" name="mpw">
    <input type="checkbox" name="auto">
    <button type="submit">Login</button>
  </form>

  <c:if test="${param.result == 'error'}">
    <h1>Login Error</h1>
  </c:if>
</body>
</html>
