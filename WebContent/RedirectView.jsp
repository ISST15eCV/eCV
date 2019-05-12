<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Redirect</title>
	<style type="text/css">
		<%@ include file="css/userView.css" %>
	</style>
</head>

<body>
	<h3>Te estamos redirigiendo a eCV</h3>
</body>

<script type="text/javascript">
	 function closeWindow() {
	    setTimeout(function() {
	    	
	    	window.opener.location.reload();
	    	window.close();
	    	}, 1500);
	    }
	
	    window.onload = closeWindow();
	</script>
	
</html>
