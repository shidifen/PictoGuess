<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PictoGuess | Log in or Register</title>
</head>
<body>
<h1>Welcome to PictoGuess</h1>
<div><b>What is this about?</b></div>
This is my Spring MVC/databases learning project. <br><br>
<div><b>Rules</b></div>
Once you sign up, you will be presented a random picture uploaded by a fellow user (10 pictures are there already, put by a fairy perhaps). Guess what it is!</br>
Write into the box the same string as that user, and if you are right, your score will increase.</br>

<hr>
There are <b>${picNr}</b> pictures currently in the database,<br>
uploaded by <b>${userNr-1}</b> awesome users.
<hr>
<a href="login">Log In</a><br>
<a href="register">Register</a> (for new users)

</body>
</html>