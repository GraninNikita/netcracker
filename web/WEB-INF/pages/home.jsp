<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 01.11.2016
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <jsp:useBean id="controller" class="com.netcracker.HelloController" scope="request"/>
    <% controller.handleLogout(request); %>
</head>
<body>
<h1>It's a home matherfuka</h1>

<tr>
    <td class="label">Principal</td>
    <td><span id="username"><%=request.getUserPrincipal().getName()%></span></td>
</tr>
</body>
</html>
