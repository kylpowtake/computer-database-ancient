<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<!-- <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
 -->
<link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/font-awesome.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="css/main.css" />" rel="stylesheet"
	media="screen">

<script src="<c:url value="js/jquery.min.js" />"></script>
<script src="<c:url value="js/bootstrap.min.js" />"></script>
<script src="<c:url value="js/dashboard.js" />"></script>


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
			<h1 id="homeTitle">${page.getNombreComputers()}
				<fmt:message key="computersFound" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<fmt:message key="searchName" />" /> <input
							type="submit" id="searchsubmit" value="${filterByName}"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addcomputer" href="addcomputer"><fmt:message
							key="addComputer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message
							key="edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="dashboard?orderby=id"><fmt:message
									key="computerId" /></a></th>
						<th><a href="dashboard?orderby=name"><fmt:message
									key="computerName" /></a></th>
						<th><a href="dashboard?orderby=introduced"><fmt:message
									key="introducedDate" /></a></th>
						<th><a href="dashboard?orderby=discontinued"><fmt:message
									key="discontinuedDate" /></a></th>
						<th><a href="dashboard?orderby=company.name"><fmt:message
									key="company" /></a></th>


					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.getComputers()}" var="computer"
						varStatus="loop">
						<tr>
							<td class="editMode"><input type="checkbox"
								name="${loop.index}" form="deleteForm" class="cb"
								value="${computer.id}"></td>
							<td><a href="editComputer?computerId=${computer.id}"
								onclick="">${computer.id}</a></td>
							<th>${computer.name}</th>
							<th>${computer.introduced}</th>
							<!-- Table header for Discontinued Date -->
							<th>${computer.discontinued}</th>
							<!-- Table header for Company -->
							<th>${computer.company.name}</th>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">

		<div class="btn-group btn-group-sm pull-right" role="group">
			<button onclick="location.href='dashboard?lang=fr'" type="button"
				class="btn btn-default">
				<fmt:message key="fr" />
			</button>
			<button onclick="location.href='dashboard?lang=en'" type="button"
				class="btn btn-default">
				<fmt:message key="en" />
			</button>
		</div>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<button onclick="location.href='dashboard?nombreParPage=10'"
				type="button" class="btn btn-default">10</button>
			<button onclick="location.href='dashboard?nombreParPage=50'"
				type="button" class="btn btn-default">50</button>
			<button onclick="location.href='dashboard?nombreParPage=100'"
				type="button" class="btn btn-default">100</button>
		</div>

		<div class="container text-center">
			<ul class="pagination">
				<li><a href="dashboard?previous=true" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a href="dashboard?numeroPage=0">1</a></li>
				<li><a href="dashboard?numeroPage=1">2</a></li>
				<li><a href="dashboard?numeroPage=2">3</a></li>
				<li><a href="dashboard?numeroPage=3">4</a></li>
				<li><a href="dashboard?numeroPage=4">5</a></li>
				<li><a href="dashboard?next=true" aria-label="Next"> <span
						aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>


	</footer>
	<!-- 	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script> -->

</body>
</html>