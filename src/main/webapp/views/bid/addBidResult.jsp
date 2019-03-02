<%--
  Created by IntelliJ IDEA.
  User: yasaman
  Date: 3/2/19
  Time: 10:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<body>
    <div style="text-align: center; align-items: center; height: 100%;">
        <c:choose>
            <c:when test="${isSuccess}">
                <title>BID_ADD_SUCCESS</title>
                <body>
                <h1>Your bid is successfully added.</h1>
                <p>The results will be announced later.</p>
                </body>
            </c:when>
            <c:otherwise>
                <title>BID_ADD_FAIL</title>
                <body>
                <h1>The budget of the project is less than your offer.</h1>
                <p>Check the budget limit and try again.</p>
                </body>
            </c:otherwise>
        </c:choose>
        <form action="${projectPath}" method="get">
            <input type="submit" value="Go to project page.">
        </form>
    </div>
</body>
</html>
