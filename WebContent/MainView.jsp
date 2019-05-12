<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main View</title>
	<style type="text/css">
		<%@ include file="css/mainView.css" %>
	</style>
</head>
<body>
	<shiro:guest>
		<div class="mainDiv">
		
			<div class="header">
			
				<div class="headerContent">
				
					<div class="logoDiv">
						<img id="logo" src="img/logo.png">
						<h1>eCV</h1>
					</div>
					
					<div class="bttns">
							<form action="MainLogServlet" method="post"><button class="headerButton" type="submit">Login</button></form>
							<form action="MainRegServlet" method="post"><button class="headerButton" type="submit">Registrate</button></form>
					</div>
					
				</div>
				
			</div>
			
			<div class="mainViewDiv">
				
			</div>
			<img id="roll" src="img/roll.png">
		</div>
	</shiro:guest>
</body>
</html>