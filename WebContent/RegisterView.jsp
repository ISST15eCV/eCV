<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register View</title>
	<style type="text/css">
		<%@ include file="css/registerView.css" %>
	</style>
</head>
<body>
	<shiro:guest>
		<div class="mainDiv">
			<div class="signin-content clearfix">
				<div class="div-logo">
					<span title="eCV">
                		<img src="img/logo.png" width="60" height="75">
                	</span>
				</div>
				<div class="accessDiv">
					<h2>Registrarse</h2>
					<div class="divisor">
                        <span class="texto-divisor">
                        	Registro en eCV
                        </span>
                    </div>
					<form action="CreateUsuarioServlet" method="post">
						<c:if test="${errorTag == 2}">
							<br><span class="errorText">*Deben rellenarse todos los campos para continuar con el registro.</span>
						</c:if>
					
						<h3 class="sectionName">Nombre de usuario*</h3>
						<br><input type="text" name="name" placeholder="Introduzca su nombre" required/>
						
						<h3 class="sectionName">Apellidos*</h3>
						<br><input type="text" name="sname" placeholder="Introduzca sus apellidos" required/>
						
						<h3 class="sectionName">NIF*</h3>
						<br><input type="text" name="nif" placeholder="Introduzca su NIF" required/>
						
						<h3 class="sectionName">Fecha de nacimiento*</h3>
						<span class="siglas_nacimiento" >(dd/mm/aaaa)</span>
							
						<div class="nacimiento">
							<br><input type="text" name="bday" placeholder="Día" maxlength="2" required/>
							<input type="text" name="bmonth" placeholder="Mes" maxlength="2" required/>
							<input type="text" name="byear" placeholder="Año" maxlength="4" required/>
							<c:if test="${errorTag == 4}">
								<br><span class="errorText">*La fecha introducida es errónea. Por favor, vuelva a escribirla.</span>
							</c:if>
						</div>
						
						<h3 class="sectionName">Correo electrónica*</h3>
							<br><input type="text" name="email" placeholder="p.ej. ecv@gmail.com" required/>
						<c:if test="${errorTag == 1}">
							<span class="errorText">*El correo que ha introducido ya está asociado a una cuenta.*</span>
						</c:if>
						
						<h3 class="sectionName">Contraseña*</h3> 
						<br><input type="password" name="password" placeholder="Contraseña" required/>
				
						<h3 class="sectionName">Repita la contraseña*</h3> 
						<br><input type="password" name="password2" placeholder="Repita la contraseña" required/>
						<c:if test="${errorTag == 3}">
							<br><span class="errorText">*Las contraseñas que ha introducido no coinciden</span>
						</c:if>
						
						<br><button class="logButton" type="submit">Registrarse</button>
					</form>
				</div>
				<form action="ReturnToMainServlet" method="post"><button class="backButton" type="submit"></i>Volver a la página principal</button></form>
			</div>
			<div class="seccion-registro">
                <p class="text-center">¿Ya tiene una cuenta?  <a href="LoginView.jsp">Iniciar sesión</a></p>       
           	</div>	
		</div>
	</shiro:guest>
</body>
</html>