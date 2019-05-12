<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Datos</title>
</head>
<body>
<shiro:guest>
	
</shiro:guest>
<shiro:user>
	<ul>
		<c:forEach items="${datos}" var="datoi">
			<li>${datoi.nameData}</li>
		</c:forEach>
	</ul>
</shiro:user>
</body>
</html>