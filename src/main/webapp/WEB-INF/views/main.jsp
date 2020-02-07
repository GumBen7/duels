<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body>
<h1>Hi, ${login}</h1>
<form action="${pageContext.request.contextPath}/duel" method="post">
    <input type="hidden" name="login" value="${login}">
    <input type="submit" name="duel" value="Duels">
</form>
<form action="${pageContext.request.contextPath}/" method="post">
    <input type="submit" name="logout" value="Log out">
</form>
</body>
</html>