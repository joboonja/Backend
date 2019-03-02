<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="User : ${user.firstName}"/></title>
</head>
<body>
    <div style="direction: rtl">
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
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </li>
        Add Skill:
        <form action="/addSkill" method="post">
            <select name="skill">
                <c:forEach var="skill" items="${availableSkills}">
                    <option value="${skill.name}">
                        <c:out value="${skill.name}"/>
                    </option>
                </c:forEach>
            </select>
            <input type="submit" value="Add"/>
        </form>
    </div>
</body>
</html>
