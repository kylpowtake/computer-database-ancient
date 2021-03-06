<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
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
					<div class="label label-default pull-right">id: ${computerId}</div>
					<h1>Edit Computer</h1>
					<form action="editComputer" method="POST">
						<input type="hidden" value="${computerId}" id="computerId"
						name="computerId" />
						<span class="erreur">${erreurs['computerId']}</span>
						<fieldset>
							<div class="form-group">
								<label for="name">Computer name</label> <input
									type="text" class="form-control" id="name"
									name="name"
									value="${name}" placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced"
									value="${introduced}" pattern="\d{4}-\d{2}-\d{2}"
									placeholder="Introduced date">
									<span class="erreur">${erreurs['introduced']}</span>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued"
									value="${discontinued}" pattern="\d{4}-\d{2}-\d{2}"
									placeholder="Discontinued date">
									<span class="erreur">${erreurs['discontinued']}</span>
									<span class="erreur">${erreurs['erreurChronologie']}</span>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${listcompanies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
								<span class="erreur">${erreurs['companyId']}</span>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>