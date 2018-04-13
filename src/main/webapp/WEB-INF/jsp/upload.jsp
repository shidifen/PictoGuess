<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload</title>
</head>

<script type="text/javascript">
	function validateForm() {
		var a = document.forms["upload"]["file"].value;
		var b = document.forms["upload"]["desc"].value;

		if ( b == null || b == "" ) {
			alert("Please fill out the description");
			return false;
		}

		if (document.getElementById("file").value == "") {
			alert("There's no picture, please upload one");
			return false;
		}

	}
</script>

<body>
	<div>Welcome to the Upload page. Due to ever increasing hosting costs, we will only approve of 
	uploading <em><i>.jpg</i></em> files. Thank you for understanding!
	</div>
	<br>
	Your current score is ${score}.<br>
	<div>
		${fileError}
		<form:form method="POST" name="upload"
			onsubmit="return validateForm()" enctype="multipart/form-data"
			action="/upload">
			<table>
				<tr>
					<td>File to upload:</td>
					<td><input id="file" type="file" name="file" accept="image/*" /></td>
					<td><input type="text" name="desc" />
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Upload" /></td>
				</tr>
			</table>
		</form:form>
	</div>

<br>
${message}
<hr>
Go <a href="/home">home</a> or <a href="/play">play</a>!<br>
  <a href="/logout">Exit</a>
</body>
</html>
