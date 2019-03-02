<%--
  Created by IntelliJ IDEA.
  User: yasaman
  Date: 3/1/19
  Time: 3:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${project.ID}"/></li>
    <li>title: <c:out value="${project.title}"/></li>
    <li>description: <c:out value="${project.description}"/></li>
    <li>imageUrl: <img src= "${project.imageURL}"  style="width: 50px; height: 50px;"></li>
    <li>budget: <c:out value="${project.budget}"/> </li>
</ul>
<!-- display form if user has not bidded before -->
<%--<form action="" method="">--%>
    <%--<label for="bidAmount">Bid Amount:</label>--%>
    <%--<input type="number" name="bidAmount">--%>

    <%--<button>Submit</button>--%>
<%--</form>--%>
</body>
</html>