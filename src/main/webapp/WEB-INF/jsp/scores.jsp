<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hall of Fame | PictoGuess</title>
</head>
<body>
<em>This is a very long list with all the users and their scores sorted in descending order.<br>
Your position is highlighted. Press Ctrl+F to search for your name.</em>
<hr>
<br>

<c:forEach items="${users}" var="user" varStatus="loop">
	
	<c:if test = "${user.name == ourUser.name}">
   			<hr>
   	</c:if>
   	
	<code> <em>${loop.index+1}  </em>  ${user.name}  ${user.score}</code>  <br>
	
	<c:if test = "${user.name == ourUser.name}">
   			<hr>
   	</c:if>
   	
</c:forEach>


<h4>The end!</h4>
<br>
<hr>
Go <a href="/home">back home</a>!<br>
<a href="/upload">Upload</a> a picture. Earn ${amountUpload} points per upload!<br>
<a href="/play">Play</a>! You will earn ${amountGuess} points per guess!<br>
<a href="/logout">Exit</a>
</body>
</html>