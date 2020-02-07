<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Duel</title>
</head>
<body>
<c:if test="${rating != null}">
    <p>Rating: ${rating}</p>
    <form action="${pageContext.request.contextPath}/duel" method="post">
        <input type="hidden" name="game" value="game">
        <input type="hidden" name="login" value="${login}">
        <input type="submit" name="start" value="Start">
    </form>
</c:if>
<c:if test="${pending != null}">
    <p>Searching an opponent</p>
</c:if>
<c:if test="${duel != null}">
    <p>DUEL</p>
</c:if>
</body>
</html>
