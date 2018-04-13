<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Let's play! | PictoGuess</title>


</head>

<script type="text/javascript">
	function validateForm() {
		var a = document.forms["submit"]["desc"].value;

		if (a == null || a == "") {
			alert("Please fill out the description");
			return false;
		}
	}
</script>

<body>
	<div style="width: 800px; margin: 0 auto;">
		Current score: ${score}<br> Total number of guessed pictures:
		${nrGuessed}<br> <br> A number of <b>${nrNotGuessed}</b>
		pictures are waiting for you to guess!
		<hr>
		<div style="margin: 0 auto;">
		<img src="pics/${pic.name}" alt="A picture" height="300" width="400">
		</div>
		<form:form method="POST" action="/play" name="submit"
			onsubmit="return validateForm()">
			<table>
				<tr>
					<td>What's in this picture?</td>
					<td><input type="text" name="desc" " /></td>
					<td><input type="submit" value="Guess" /></td>
				</tr>
			</table>
		</form:form>
		
		You have <b>${nrOfTries}</b> chances to guess. If you fail the last
		time, a new picture will be presented to you. <br> You can also <a
			href="/play?skip=true">skip</a> this picture. <br>
	</div>

	<hr>
	<a href="/home">Home</a>
	<br>
	<a href="/upload">Upload</a>
	<br>
	<a href="/scores">Scores</a>
	<br>
	<a href="/logout">Exit</a>

</body>
</html>