<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/font-awesome.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/main.css" />" rel="stylesheet"
	media="screen">
<script src="<c:url value="js/bootstrap.min.js" />"></script>

<title>EL POWO LOCO : OCOL OWOP EL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <fmt:message key="header" /> </a>
		</div>
	</header>
	
	<p>POOOOOOOOOOWOOOOOOOOOOOOOOOOOOOO
		LOOOOOOOOOOCOOOOOOOOOOOOOOOOOOOO</p>
		
			<footer class="navbar-fixed-bottom">

		<div class="btn-group btn-group-sm pull-right" role="group">
			<button onclick="location.href='?lang=fr'" type="button"
				class="btn btn-default">
				<fmt:message key="fr" />
			</button>
			<button onclick="location.href='?lang=en'" type="button"
				class="btn btn-default">
				<fmt:message key="en" />
			</button>
		</div>
	</footer>
</body>
</html>