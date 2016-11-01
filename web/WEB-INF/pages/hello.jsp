<%@ page contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Hello World!</title>
</head>
<body>
<jsp:useBean id="controller" class="com.netcracker.HelloController" scope="request"/>
<% controller.handleLogout(request); %>

<%--<h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>--%>

<button onclick="location.href = 'home'" type="button">Login</button>
<button onclick="location.href = 'hello?logout=true'" type="button">Logout</button>
</body>
</html>