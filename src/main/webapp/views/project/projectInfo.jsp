<%--
  Created by IntelliJ IDEA.
  User: yasaman
  Date: 3/1/19
  Time: 3:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>
<ul>
    <li>id: 1</li>
    <li>title: project title 1</li>
    <li>description: This is a description</li>
    <li>imageUrl: <img src="url of your image" style="width: 50px; height: 50px;"></li>
    <li>budget: 3750000</li>
</ul>
<!-- display form if user has not bidded before -->
<form action="" method="">
    <label for="bidAmount">Bid Amount:</label>
    <input type="number" name="bidAmount">

    <button>Submit</button>
</form>
</body>
</html>