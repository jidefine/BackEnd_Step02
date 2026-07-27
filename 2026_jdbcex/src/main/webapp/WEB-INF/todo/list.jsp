<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ugo20
  Date: 2026-07-27
  Time: 오후 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo List</title>
</head>
<body>
  <h1>Todo List</h1>
  <ul>
    <c:forEach items="${dtoList}" var="dto">
      <li>${dto}</li>
    </c:forEach>
  </ul>
</body>
</html>
