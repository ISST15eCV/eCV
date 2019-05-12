<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CareerView</title>
	<style type="text/css">
		<%@ include file="css/careerView.css" %>
	</style>
</head>
<body>
	<shiro:lacksRole name="usuario">
	No tienes permiso para ver el contenido de esta página
	</shiro:lacksRole>
	<shiro:hasRole name="usuario">
	
			<div class="mainDiv">
			
				<div class="header">
					<div class=headerContent>
						<div class="logoDiv">
							<img id="logo" src="img/logo.png">
							<h1>eCV</h1>
						</div>
						
						<form action="PDFServlet" method="get">
							<input type="hidden" name="email" value="${usuario.email}"/>
							<button class="exportBttn" type="submit">Exportar PDF</button>
						</form>
						
						<form action="CVServlet" method="get">
							<input type="hidden" name="email" value="${usuario.email}"/>
							<button class="exportBttn" type="submit" formtarget="_blank">Mis CV</button>
						</form>
						
						<c:if test="${usuario.photo != null}">
							<img id="imagenUsuario" src="data:image/jpeg;base64,${photo}" width="100" height="100" />
						</c:if>
						
						<div class="bttn">
							<span>¡Bienvenido/a, ${usuario.pd.name}!</span>
							<form action="LogoutServlet" method="get"><button class="headerButton" type="submit">Logout</button></form>					
						</div>
					</div>							
				</div>
				
				<div class="userViewDiv">
					<div class="navigationBar">
						<div class="navigationBarContent">
							<h2 class="pageName">Mis Datos</h2>
							<div class="botonera">
					
								<form action="ToUserServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<button class="menuButton" type="submit">Datos Personales</button>
								</form>
								
								<button class="menuButton" type="submit"  disabled>Datos Profesionales</button>
								
								<form action="ToStudiesServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<button class="menuButton" type="submit">Datos Académicos</button>
								</form>
							</div>
						</div>
					</div>
		
					<div class="datos">
						<h7 class="sectionTitle">Datos Profesionales</h7>
						
							<div class="dataSection">
							
								<h3>Empleo Actual</h3>
								
								<table class="jobTable" border="1">
									<thead>
										<tr>
											<th class="cat" >Categoria</th>
											<th class="ent">Entidad Empleadora</th>
											<th class="date">Fecha de inicio</th>
										</tr>
									</thead>
									<tbody>
										<tr class="trformat">
											<th>${usuario.career.currentJob.category}</th>
											<td>${usuario.career.currentJob.employer}</td>
											<td>${day}-${month}-${year}</td>
										</tr>
									</tbody>
								</table>
								
								<h5>Modificar la situación actual:</h5>
								<div><form action="UpdateCJServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<input type="hidden" name="careerId" value="${usuario.career.id}" />
									<input type="hidden" name="currentJobId" value="${usuario.career.currentJob.id}" />
									<div class="datosForm">
										<span class="campo">Categoría:<input type="text" name="cat" placeholder="Categoría" required /></span>
										<span class="campo">Entidad:<input type="text" name="ent" placeholder="Entidad" required /></span>
									</div>
									<div class="fechaForm">									
										<span class="campo">Día:<input type="text" name="day" placeholder="dd" maxlength="2" required /></span>
										<span class="campo">Mes:<input type="text" name="month" placeholder="mm" maxlength="2" required /></span>
										<span class="campo">Año:<input type="text" name="year" placeholder="aaaa" maxlength="4" required /></span>
									</div>								
								<button class="saveButton" type="submit">Guardar</button>
								<c:if test="${errorTag == 4}">
									<br><span class="errorText">La fecha introducida es errónea</span>
								</c:if>
								</form></div>
								
								<h3>Antiguos empleos</h3>
								<table class="oldjobTable" border="1">
									<thead>
										<tr>
											<th class="cat">Categoría</td>
											<th class="ent">Entidad Empleadora</th>
											<th class="date">Fecha de inicio</th>
											<th class="act">Borrar</th>
										</tr>
									</thead>
									<tbody>
										
										<c:forEach items="${usuario.career.previousJobs}" var="pji">
											<tr class="trformat">
												<th>${pji.category}</th>
												<td>${pji.employer}</td>
												<td>${pji.start}</td>
												<td class="removetd"><form action="RemoveJobServlet" method="post">
													<input type="hidden" name="email" value="${usuario.email}" />
													<input type="hidden" name="jobId" value="${pji.id}">
													<button class="removeButton" type="submit">Borrar</button>
												</form></td>
											</tr>
										</c:forEach>
									
									</tbody>
								</table>
								
								<h5>Modificar datos:</h5>
								<div class="formulario"><form action="AddJobServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<input type="hidden" name="careerId" value="${usuario.career.id}" />
									<div class="datosForm">
										<span class="campo">Categoría:<input type="text" name="cat" placeholder="Categoría" required /></span>
										<span class="campo">Entidad:<input type="text" name="ent" placeholder="Entidad" required /></span>
									</div>
									<div class="fechaForm">
										<span class="campo">Día:<input type="text" name="day" placeholder="dd" maxlength="2" required /></span>
										<span class="campo">Mes:<input type="text" name="month" placeholder="mm" maxlength="2" required /></span>
										<span class="campo">Año:<input type="text" name="year" placeholder="aaaa" maxlength="4" required /></span>
									</div>
								<button class="saveButton" type="submit">Guardar</button>
								<c:if test="${errorTag == 2}">
									<br><span class="errorText">La fecha introducida es errónea</span>
								</c:if>
								
								</form>								
							</div>						
					</div>
				</div>
		
			</div>
	</shiro:hasRole>
</body>
</html>