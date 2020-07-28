<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="UTF-8">
<title>Insert title here</title>

<link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/font-awesome.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/main.css" />" rel="stylesheet"
	media="screen">

<script src="<c:url value="js/jquery.min.js" />"></script>
</head>

<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <fmt:message
					key="header" />
			</a>
		</div>
		<div class="btn-group btn-group-sm pull-right" role="group">
			<button onclick="location.href='logout'" type="button"
				class="btn btn-default">
				<fmt:message key="logout" />
			</button>
		</div>
	</header>


	<form class="form-signin navbar navbar-default" method="POST"
		action="<c:url value='/perform_login'/>">
		<div class="form-signin-img">
			<span class="form-signin-img-helper"></span> <img
				src="<c:url value='/img/itensis_logo.gif'/>" />
		</div>
		<h2 class="form-signin-heading">${msg_heading}</h2>
		<c:if test="${error == true}">
			<div class="alert alert-danger">
				<button type="button" class="close close-box">&times;</button>
				<p>${msg_error}</p>
			</div>
		</c:if>
		<input type="text" name="security_username" id="security_username"
			class="form-control" placeholder="${msg_username}" required autofocus>
		<input type="password" name="security_password" id="security_password"
			class="form-control" placeholder="${msg_password}" required>

		<label class="checkbox"> <input type="checkbox"
			name="remember_me_checkbox"> ${msg_rememberMe}
		</label>
		<button class="btn btn-lg btn-primary btn-block" type="submit">
			<i class="fa fa-sign-in fa-lg"></i> <span>${msg_login}</span>
		</button>
	</form>



	<div class="btn-group btn-group-sm pull-right" role="group">
		<button onclick="location.href='login?lang=fr'" type="button"
			class="btn btn-default">
			<fmt:message key="fr" />
		</button>
		<button onclick="location.href='login?lang=en'" type="button"
			class="btn btn-default">
			<fmt:message key="en" />
		</button>
	</div>
</body>
</html>