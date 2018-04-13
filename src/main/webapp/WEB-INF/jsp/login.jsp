<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    </head>
    <title>Log In | PictoGuess</title>
    <body>
        <h3>Please enter your name and password</h3>
        <c:if test="${not empty userPasswordIncorrect}">
   			${userPasswordIncorrect} <a href="/register">Register</a>
		</c:if>
		<c:if test="${not empty userPasswordEmpty}">
   			${userPasswordEmpty}
		</c:if>
        <form:form method="POST" action="/login" modelAttribute="user">
             <table>
                <tr>
                    <td><form:label path="name">Name</form:label></td>
                    <td><form:input path="name"/></td>
                </tr>
                <tr>
                    <td><form:label path="password">Password</form:label></td>
                    <td><form:input type="password" path="password"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Enter"/></td>
                </tr>
            </table>
        </form:form>
<hr>
Go <a href="/">back</a> or <a href="/">register</a>!<br>    
</body>
</html>