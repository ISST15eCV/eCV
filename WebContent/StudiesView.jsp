<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>StudiesView</title>
	<style type="text/css">
		<%@ include file="css/studiesView.css" %>
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
							
						<form action="PDFServlet" method="get">
							<input type="hidden" name="email" value="${usuario.email}"/>
							<button class="exportBttn" type="submit" formtarget="_blank">Exportar PDF</button>
						</form>
						
						<form action="CVServlet" method="get">
							<input type="hidden" name="email" value="${usuario.email}"/>
							<button class="exportBttn" type="submit">Mis CV</button>
						</form>
						
						<c:if test="${usuario.photo != null}">
							<img id="imagenUsuario" src="data:image/jpeg;base64,${photo}" width="100" height="100" />
						</c:if>
							
						<div class="bttns">
							<h6>¡Bienvenido/a, ${usuario.pd.name}!</h6>
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
								
								<form action="ToCareerServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<button class="menuButton" type="submit">Datos Profesionales</button>
								</form>
								
								<button class="menuButton" type="submit" disabled>Datos Académicos</button>
								
							</div>
						</div>
					</div>
					<div>
						<h7>Información académica</h7>
						<div class="dataSection">
							<h3>Titulaciones</h3>
								<table class="titleTable" border="1">
									<thead>
										<tr>
											<th class="name">Nombre de la titulación</th>
											<th class="ent">Entidad de titulación</th>
											<th class="date">Fecha de titulación</th>
											<th class="act">Borrar</th>
										</tr>
									</thead>
									<tbody>
											<c:forEach items="${usuario.studies.degrees}" var="degreei">
												<tr class="trformat">
													<td>${degreei.name}</td>
													<td>${degreei.place}</td>
													<td>${degreei.date}</td>
													<td class="removetd"><form action="RemoveDegreeServlet" method="post">
														<input type="hidden" name="email" value="${usuario.email}" />
														<input type="hidden" name="degreeId" value="${degreei.id}">
														<button class="removeButton" type="submit">Borrar</button>
													</form></td>
												</tr>
											</c:forEach>
									</tbody>
								</table>
								<h5>Modificar datos:</h5>
								
								<div class="formulario"><form action="AddDegreeServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<input type="hidden" name="studiesId" value="${usuario.studies.id}" />
									<div class="datosForm">
										<span class="campo">Titulación:<input type="text" name="name" placeholder="Nombre de la titulación" required /></span>
										<span class="campo">Entidad:<input type="text" name="ent" placeholder="Entidad de la titulación" required /></span>
									</div>
									<div class="fechaForm">
										<span class="campo">Día:<input type="text" name="day" placeholder="dd" maxlength="2" required /></span>
										<span class="campo">Mes:<input type="text" name="month" placeholder="mm" maxlength="2" required /></span>
										<span class="campo">Año:<input type="text" name="year" placeholder="aaaa" maxlength="4" required /></span>
									</div>
									<button class="saveButton" type="submit">Guardar</button>
								<c:if test="${errorTag == 1}">
									<br><span class="errorText">La fecha introducida es errónea</span>
								</c:if>			
								</form>								
								</div>
								
								<h3>Doctorados</h3>
								<table class="titleTable "border="1">
									<thead>
										<tr>
											<th class="name">Nombre de la titulación</th>
											<th class="ent">Entidad de titulación</th>
											<th class="date">Fecha de titulación</th>
											<th class= "act">Borrar</th>
										</tr>
									</thead>
									<tbody>
										
										<c:forEach items="${usuario.studies.phds}" var="phdi">
											<tr class="trformat">
												<td>${phdi.name}</td>
												<td>${phdi.place}</td>
												<td>${phdi.date}</td>
												<td class="removetd"><form action="RemovePhdServlet" method="post">
													<input type="hidden" name="email" value="${usuario.email}" />
													<input type="hidden" name="phdId" value="${phdi.id}">
													<button class="removeButton" type="submit">Borrar</button>
												</form></td>
											</tr>
										</c:forEach>
										
									</tbody>
								</table>
								<h5>Modificar datos:</h5>
								
								<div class="formulario"><form action="AddPhdServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<input type="hidden" name="studiesId" value="${usuario.studies.id}" />
									<div class="datosForm">
										<span class="campo">Titulación:<input type="text" name="name" placeholder="Nombre de la titulación" required /></span>
										<span class="campo">Entidad:<input type="text" name="ent" placeholder="Entidad de la titulación" required /></span>
									</div>
									<div class="fechaForm">
										<span class="campo">Día:<input type="text" name="day" placeholder="dd" maxlength="2" required /></span>
										<span class="campo">Mes:<input type="text" name="month" placeholder="mm" maxlength="2" required /></span>
										<span class="campo">Año:<input type="text" name="year" placeholder="aaaa" maxlength="4" required /></span>
									</div>
								<button class="saveButton" type="submit">Guardar</button>
								<c:if test="${errorTag == 3}">
									<br><span class="errorText">La fecha introducida es errónea</span>
								</c:if>
								</form>								
								</div>
								
								<h3>Idiomas</h3>
								<table class=langTable border="1">
									<thead>
										<tr>
											<th class="idioma">Idioma</th>
											<th class="listen">Comprensión auditiva</th>
											<th class="reading">Comprensión lectora</th>
											<th class="speaking">Interacción oral</th>
											<th class="act">Borrar</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${usuario.studies.languages}" var="langi">
											<tr class="trformat">
												<td>${langi.name}</td>
												<td>${langi.listeningLevel}</td>
												<td>${langi.readingLevel}</td>
												<td>${langi.speakingLevel}</td>
												<td class="removetd"><form action="RemoveLanguageServlet" method="post">
													<input type="hidden" name="email" value="${usuario.email}" />
													<input type="hidden" name="langId" value="${langi.id}">
													<button class="removeButton" type="submit">Borrar</button>
												</form></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<h5>Modificar datos:</h5>
								
								<div class= formulario><form action="AddLanguageServlet" method="post">
									<input type="hidden" name="email" value="${usuario.email}" />
									<input type="hidden" name="studiesId" value="${usuario.studies.id}" />
									<div class=datosForm>
										<span class="campo">Idioma:<input type="text" name="name" placeholder="Idioma" required /></span>
										<span class="campo">Comprensión audittiva:<input type="text" name="lst" placeholder="Comprensión auditiva"required  /></span>
									</div>
									<div class="fechaForm">
										<span class="campo">Comprensión lectora:<input type="text" name="rdg" placeholder="Comprensión lectora"required  /></span>
										<span class="campo">Interación oral:<input type="text" name="spk" placeholder="Interacción oral"required  /></span>
									</div>
									<button class="saveButton" type="submit">Guardar</button>
								</form>								
								</div>
								
															
						</div>
					</div>
				</div>
		
			</div>
	</shiro:hasRole>
</body>
</html>