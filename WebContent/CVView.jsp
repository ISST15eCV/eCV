<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserView</title>
	<style type="text/css">
		<%@ include file="css/CVView.css" %>
	</style>
</head>
<body>
	<shiro:lacksRole name="usuario">
	No tienes permiso para ver el contenido de esta página
	</shiro:lacksRole>
	<shiro:hasRole name="usuario">
	
			<div class="mainDiv">
			
				<div class="header">
	
					<div class="headerContent">
					
						<div class="logoDiv">
							<img id="logo" src="img/logo.png">
							<h1>eCV</h1>
						</div>
						
							<form action="ToUserServlet" method="post">
								<input type="hidden" name="email" value="${usuario.email}"/>
								<button class="exportBttn" type="submit">Mis Datos</button>
							</form>
					
						<div class="bttns">
							<h6>¡Bienvenido/a, ${usuario.pd.name}!</h6>
							<form action="LogoutServlet" method="get"><button class="headerButton" type="submit">Logout</button></form>
						</div>
						
						
					</div>			
				
				</div>
				
				<div class="CVViewDiv">
					<h2>Mis CV</h2>
					<table class="cvTable" border="1">
						<thead>
							<tr>
								<th class="date">Fecha de creación</th>
								<th class="descargar">Descargar</th>
								<th class="borrar">Borrar</th>
							</tr>	
						</thead>
						<tbody>
							<c:forEach items="${usuario.cvs}" var="cvi">
								<tr class="trformat">
									<th>${cvi.date}</th>
									<td class=removetd><form action="DownloadCVServlet" method="get">
											<input type="hidden" name="cvId" value="${cvi.id}">
											<button class="removeButton" type="submit">Descargar</button></form></td>
									<td class=removetd><form action="RemoveCVServlet" method="post">
											<input type="hidden" name="email" value="${usuario.email}" />
											<input type="hidden" name="cvId" value="${cvi.id}">
											<button class="removeButton" type="submit">Borrar</button></form></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
		
			</div>
	</shiro:hasRole>
</body>
</html>
