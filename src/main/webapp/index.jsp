<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Duels</title>
</head>
<body>
<h1>Welcome to Duels!</h1>
<h2>Please put LOGIN and PASSWORD to play</h2>
<c:if test="${violation != null}">
    <p>${violation}</p>
</c:if>
<form action="${pageContext.request.contextPath}/main" method="post">
    <label for="login">LOGIN: </label>
    <input type="text" name="login" id="login" value="${login}">
    <label for="password">PASSWORD: </label>
    <input type="password" name="password" id="password" value="${password}">
    <input type="submit" name="signin" value="Sign In">
</form>
</body>
</html>
