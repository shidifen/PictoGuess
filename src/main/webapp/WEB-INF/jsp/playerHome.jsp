<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home | PictoGuess</title>
</head>

<body>
    <h2>Welcome, ${dname}!</h2><br>
    
    Your current score is ${score}. Increase it by playing or uploading pictures.
    <br>
    ${outOfPics}<br>
    
    <hr>
    
   <a href="/upload">Upload</a> a picture. Earn ${amountUpload} points per upload!<br><br>
   <a href="/play">Play</a>! Earn ${amountGuess} points per correct guess!<br><br>
   <a href="/scores">Scores</a><br><br>
   <a href="/logout">Exit</a>
</body>
</html>