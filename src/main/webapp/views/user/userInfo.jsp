<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
    <div style="direction: rtl">
        <ul>
            <li><c:out value="${user.id}"/> </li>
            <li><c:out value="${user.firstName}"/></li>
            <li><c:out value="${user.lastName}"/></li>
            <li><c:out value="${user.jobTitle}"/></li>
            <li><c:out value="${user.bio}"/></li>
            <li>
                skills:
                <ul>
                    <c:forEach var="skill" items="${skills}">
                        <li>
                            <c:out value="${skill.name}"/>
                            <form action="/endorse" method="POST">
                                <input type="hidden" name="skill" value="${skill.name}"/>
                                <input type="hidden" name="id" value="${user.id}"/>
                                <c:if test="${canEndorse}">
                                    <input type="submit" value="Endorse" />
                                </c:if>
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
    </div>
</body>
</html>
