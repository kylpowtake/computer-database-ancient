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
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<fmt:message key="addComputer" />
					</h1>
					<form:form modelAttribute="user">
						<form:errors path="*" cssClass="error" />
						<fieldset>
							<div class="form-group">
								<div class="form-group">
									<label for="pseudo"><fmt:message
											key="pseudo" /></label>
									<form:input path="pseudo" />
									<form:errors path="pseudo" cssClass="error" />
								</div>
								<div class="form-group">
									<label for="password"><fmt:message
											key="password" /></label>
									<form:input path="password" />
									<form:errors path="password" cssClass="error" />
								</div>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<fmt:message key="add" />" id="Add"
								class="btn btn-primary"> or <a href="dashboard"
								class="btn btn-default"><fmt:message key="cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<div class="btn-group btn-group-sm pull-right" role="group">
		<button onclick="location.href='addComputer?lang=fr'" type="button"
			class="btn btn-default">
			<fmt:message key="fr" />
		</button>
		<button onclick="location.href='addComputer?lang=en'" type="button"
			class="btn btn-default">
			<fmt:message key="en" />
		</button>
	</div>
</body>
</html>