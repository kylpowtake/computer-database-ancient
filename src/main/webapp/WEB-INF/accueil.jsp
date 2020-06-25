<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
		</div>
	</header>
	
		<section id="main">
		<div class="container">
			<div class="alert alert-danger">
			<c:out value="Plop"></c:out>
			<c:out value="test"></c:out>
			<c:out value="${test}"></c:out>
			<%
			String attribut = (String) request.getAttribute("test");
			out.println(attribut);
			%>
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>
	
		<div class="container">
			<a class="navbar-brand" href="HTML/addComputer.html"> Envie de créer un computer ? </a>
			<br>
			<a class="navbar-brand" href="HTML/dashboard.html"> Envie de voir la liste de computers ? </a>
			<br>
			<a class="navbar-brand" href="HTML/editComputer.html"> Envie de modifier un computer ? </a>
			<br>
			<a class="navbar-brand" href=""> Envie de voir le vide ? </a>
		</div>

	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/dashboard.js"></script>
</body>
</html>