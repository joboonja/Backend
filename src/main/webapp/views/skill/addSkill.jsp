<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding Skill</title>
</head>
<body>
<div style="text-align: center; align-items: center; height: 100%;">
    <h1>
        <c:out value="You have added ${skill} to your skills."/>
    </h1>
    <form action="${userPath}" method="get">
        <input type="submit" value="Go to your profile.">
    </form>
</div>
</body>
</html>
