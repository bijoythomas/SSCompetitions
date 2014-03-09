<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<a href=<c:url value="/j_spring_security_logout"/>>Logout</a><br/>
<sec:authorize ifNotGranted="ROLE_JUDGE">
    <a href="faces/register.xhtml">Register Your Student</a><br/>
    <a href="faces/search.xhtml">Search</a><br/>
<a href="faces/piechart.xhtml">Pie Chart</a>
</sec:authorize>

<sec:authorize ifAnyGranted="ROLE_ADMIN">
    <h1>Only admin can see this</h1><br/>
    <a href="admin"> Admin Home </a>
</sec:authorize>
<sec:authorize ifAnyGranted="ROLE_JUDGE">
    <h1>Only Judge can see this</h1><br/>
    <a href="judge"> Judge Home </a>
</sec:authorize>

</body>
</html>