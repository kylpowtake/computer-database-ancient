<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="UTF-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<c:if test="${not empty erreurnamevide}">
		<c:out value="Le nom du computer est obligatoire."></c:out>
	</c:if>


	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form:form modelAttribute="computer">
						<fieldset>
							<div class="form-group">
								<div class="form-group">
									<label  for="computerName">Computer name</label>
									<form:input path="name" />
									<form:errors path="name" />
								</div>
								<div class="form-group">
									<label for="introduced">Introduced date</label>
									<form:input type="date" path="introduced" />
									<form:errors path="introduced" />
								</div>
								<div class="form-group">
									<label for="discontinued">Discontinued date</label>
									<form:input type="date" path="discontinued" />
									<form:errors path="discontinued" />
									<%--<form:errors path="chronology" /> --%>
								</div>
								<div class="form-group">
									<label for="companyId">Company</label>
									<form:select path="companyId">
										<form:option value="0" label="--Select if needed." />
										<form:options items="${companiesList}" itemValue="id"
											itemLabel="name" />
									</form:select>
								</div>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>

						<c:if test="${not empty resultat}">
							<c:out value="${resultat}"></c:out>
						</c:if>
						
					<%-- 					<form action="addComputer" method="POST"
						onsubmit="return checkDate()">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									value="${computerName}" required type="text"
									class="form-control" name="computerName" id="computerName"
									placeholder="Computer name" /> <span class="erreur">${erreurs['computerName']}</span>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									value="${introduced}" type="date" class="form-control"
									pattern="\d{4}-\d{2}-\d{2}" name="introduced" id="introduced"
									placeholder="Introduced date" /> <span class="erreur">${erreurs['introduced']}</span>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									value="${discontinued}" type="date" class="form-control"
									name="discontinued" id="discontinued"
									pattern="\d{4}-\d{2}-\d{2}" placeholder="Discontinued date" />
								<span class="erreur">${erreurs['discontinued']}</span> <span
									class="erreur">${erreurs['chronology']}</span>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<option value="0">--</option>
									<c:forEach items="${listcompanies}" var="company">
										<option value="${company.id}"><c:out
												value="${company.name}" /></option>
									</c:forEach>
								</select> <span class="erreur">${erreurs['companyId']}</span>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
						<c:if test="${not empty resultat}">
							<c:out value="${resultat}"></c:out>
						</c:if>
					</form> --%>
				</div>
			</div>
		</div>
	</section>
	<script src="js/jquery.min.js"></script>
	<script src="js/addComputer.js"></script>
</body>
</html>