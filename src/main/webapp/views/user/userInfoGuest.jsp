<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="User : ${user.firstName}"/></title>
</head>
<body>
<ul>
    <li><c:out value="ID : ${user.id}"/> </li>
    <li><c:out value="FirstName : ${user.firstName}"/></li>
    <li><c:out value="LastName : ${user.lastName}"/></li>
    <li><c:out value="Job Title : ${user.jobTitle}"/></li>
    <li><c:out value="Bio : ${user.bio}"/></li>
    <li>
        Skills:
        <ul>
            <c:forEach var="skill" items="${skills}">
                <li>
                    <c:out value="${skill.name} : ${skill.points}"/>
                    <form action="/endorse" method="POST">
                        <input type="hidden" name="skill" value="${skill.name}"/>
                        <input type="hidden" name="id" value="${user.id}"/>
                        <c:if test="${skill.canEndorse(user.id)}">
                            <input type="submit" value="Endorse" />
                        </c:if>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
