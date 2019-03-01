<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li><c:out value="${user.id}"/> </li>
    <li><c:out value="${user.firstName}"/></li>
    <li><c:out value="${user.lastName}"/></li>
    <li><c:out value="${user.jobTitle}"/></li>
    <li><c:out value="${user.bio}"/></li>
    <li>
        skills:
        <ul>
            <li>
                HTML: 2
                <form action="" method="">
                    <button>Endorse</button>
                </form>
            </li>
            <li>
                Java: 1
                <form action="" method="">
                    <button>Endorse</button>
                </form>
            </li>
            <li>
                CSS: 2
                <!-- no form if already endorsed -->
            </li>
        </ul>
    </li>
</ul>
</body>
</html>
