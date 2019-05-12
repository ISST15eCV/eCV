<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	
					<h2>Mis Datos</h2>
					<h3>Nombre</h3>
					<div><span>${usuario.pd.name} ${usuario.pd.surname}</span></div>
					<h3>Nif</h3>
					<div><span>${usuario.pd.nif}</span></div>
					<h3>Dirección</h3>
					<div>
						<span>AQUI IRIA LA DIRECCIÓN</span>
						<br><span class="letraPequeña1">Cambiar la dirección:</span>
					</div>
					<c:if test="${errorTag == 1}">
						<span class="errorText">No puede haber campos vacíos</span>
					</c:if>
					
					
					
										<h3>Nombre</h3>
					<div><span>${usuario.pd.name} ${usuario.pd.surname}</span></div>
					<h3>Nif</h3>
					<div><span>${usuario.pd.nif}</span></div>
					<h3>Dirección</h3>
					<div>
						<span>AQUI IRIA LA DIRECCIÓN</span>
						<br><span class="letraPequeña1">Cambiar la dirección:</span>
					</div>
					<c:if test="${errorTag == 1}">
						<span class="errorText">No puede haber campos vacíos</span>
					</c:if>
					
											<c:if test="${errorTag == 2}">
							<br><span class="errorText">No puede haber campos vacios</span>
						</c:if>
	
	
</body>
</html>