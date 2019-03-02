<%--
  Created by IntelliJ IDEA.
  User: yasaman
  Date: 3/2/19
  Time: 10:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test = "${isSuccess}">
        <title>Your bid is successfully added.</title>

        <body>
            <p>The results will be announced later.</p>
        </body>
    </c:if>
    <c:choose>
        <c:when test="${isSuccess}">
            <title>Your bid is successfully added.</title>
        </c:when>
        <c:otherwise>
            <title>The budget of the project is less than your offer.</title>

            <body>
                <p>Check the budget limit and try again.</p>
            </body>
        </c:otherwise>
    </c:choose>
</head>
<body>

</body>
</html>
