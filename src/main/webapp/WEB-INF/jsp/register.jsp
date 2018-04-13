<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    </head>
    <title>Register | PictoGuess</title>
    <body>
        <h3>Welcome, please enter a name and a password in order to play!</h3>
        <c:if test="${not empty userExists}">
   			${userExists}
		</c:if>
		<c:if test="${not empty userInexistent}">
   			${userInexistent}
		</c:if>
		<c:if test="${not empty userPasswordEmpty}">
   			${userPasswordEmpty}
		</c:if>
        <form:form method="POST" action="/register" modelAttribute="user">
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
                    <td><input type="submit" value="Let's go!"/></td>
                </tr>
            </table>
        </form:form>
        If you already have an account, <a href="/login">log in</a> here
    </body>
</html>