<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Judge</title>
</head>
<body>
<h1>Only Scoring Judge allowed here</h1>
<a href=<c:url value="/j_spring_security_logout"/>>Logout</a><br/>
<a href="faces/scores.xhtml">Enter Scores</a>
</body>
</html>