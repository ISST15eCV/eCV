<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insertar Datos</title>
</head>
<body>
<shiro:guest>
	<form action="InsertDataServlet" method="post">
		<p>Nombre del dato <input type="text" name="nameData"></p>
		<p>Tipo de dato </p>
		<select name="type">
	        <option value="studies">Estudios</option>
	        <option value="experience">Experiencia</option>
	        <option value="habilities">Habilidades</option>
	        <option value="languages">Idiomas</option>
	        <option value="others">Otros</option>
         </select>
		<p>Descripcion </p>
		<input type="text" name="description">
		<p><button type="submit">Insertar</button></p>
	</form>
</shiro:guest>
<shiro:user>
	<!-- algo aqui, o no -->
</shiro:user>
</body>
</html>