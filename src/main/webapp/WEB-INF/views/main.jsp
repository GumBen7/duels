<html>
<head>
    <meta charset="UTF-8">
    <title>Duels</title>
</head>
<body>
<h1>Thanks for signing up with us!</h1>
<form action="${pageContext.request.contextPath}/duel" method="post">
    <input type="submit" name="duel" value="Duels">
</form>
<form action="${pageContext.request.contextPath}/" method="post">
    <input type="submit" name="logout" value="Log out">
</form>
</body>
</html>