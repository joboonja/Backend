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
    <li>ID: <c:out value = "${project.ID}"/></li>
    <li>Title: <c:out value = "${project.title}"/></li>
    <li>Description: <c:out value = "${project.description}"/></li>
    <li>ImageUrl: <img src = "${project.imageURL}"  style="width: 50px; height: 50px;"></li>
    <li>Budget: <c:out value ="${project.budget}"/> </li>
</ul>
<!-- display form if user has not bidded before -->

<c:if test = "${canBid}">
    <form action = "/bid" method = "">
        <label> Bid Amount: </label>
        <input type = "number" name = "bidAmount">
        <input type = "hidden" name = "projectID" value = "${project.ID}">
        <input type = "hidden" name = "projectID" value = "${project.ID}">

        <button>Submit</button>
    </form>
</c:if>

</body>
</html>