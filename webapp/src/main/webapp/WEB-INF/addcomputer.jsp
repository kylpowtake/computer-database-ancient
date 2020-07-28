<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="UTF-8">
<link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/font-awesome.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/main.css" />" rel="stylesheet"
	media="screen">
<script src="<c:url value="js/jquery.min.js" />"></script>
<script src="<c:url value="js/addComputer.js" />"></script>
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
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<fmt:message key="addComputer" />
					</h1>
					<form:form modelAttribute="computer">
						<form:errors path="*" cssClass="error" />
						<fieldset>
							<div class="form-group">
								<div class="form-group">
									<label for="computerName"><fmt:message
											key="computerName" /></label>
									<form:input path="name" />
									<form:errors path="name" cssClass="error" />
								</div>
								<div class="form-group">
									<label for="introduced"><fmt:message
											key="introducedDate" /></label>
									<form:input type="date" path="introduced" />
									<form:errors path="introduced" cssClass="error" />
								</div>
								<div class="form-group">
									<label for="discontinued"><fmt:message
											key="discontinuedDate" /></label>
									<form:input type="date" path="discontinued" />
									<form:errors path="discontinued" cssClass="error" />
									<%--<form:errors path="chronology" /> --%>
								</div>
								<div class="form-group">
									<label for="companyId"><fmt:message key="company" /></label>
									<form:select path="company">
										<form:option value="0" label="-Please Select-" />
										<form:options items="${companiesList}" itemValue="id"
											itemLabel="name" />
									</form:select>
								</div>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<fmt:message key="add" />" id="Add"
								class="btn btn-primary"> or <a href="dashboard"
								class="btn btn-default"><fmt:message key="cancel" /></a>
						</div>
					</form:form>
					<c:if test="${not empty resultat}">
						<c:out value="${resultat}"></c:out>
					</c:if>
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
	<script src="js/jquery.min.js"></script>
	<script src="js/addComputer.js"></script>
</body>
</html>