<%--
  Created by IntelliJ IDEA.
  User: yasaman
  Date: 3/2/19
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="text-align: center; align-items: center; height: 100%;">
    <h1>
        <c:out value="You have deleted ${skill} successfully."/>
    </h1>
    <form action="${userPath}" method="get">
        <input type="submit" value="Go to user page.">
    </form>
</div>
</body>
</html>