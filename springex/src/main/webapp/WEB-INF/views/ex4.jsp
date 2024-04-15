<%--
  Created by IntelliJ IDEA.
  User: bitcamp1
  Date: 2024-04-15
  Time: 오후 2:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>${message}</h1>

    <h1><c:out value = "${message}"></c:out></h1>
</body>
</html>
