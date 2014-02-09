<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>

<body background="/resources/images/censer.jpg" background-size=cover>
<c:if test="${not empty param.error}">
    <font color="red">
        Login error. <br />
        Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
    </font>
</c:if>
<div style="margin-left:auto;margin-right:auto;text-align:left">
    <form method="POST" style="width:300px; height:200px; border:1px solid white; position:relative;" action="<c:url value="/j_spring_security_check" />">
        <table>
            <tr>
                <td align="right">Username</td>
                <td><input type="text" name="j_username" /></td>
            </tr>
            <tr>
                <td align="right">Password</td>
                <td><input type="password" name="j_password" /></td>
            </tr>

            <tr>
                <td colspan="2" align="right">
                    <input type="submit" value="Login" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
