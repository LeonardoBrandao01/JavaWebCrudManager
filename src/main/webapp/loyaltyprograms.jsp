<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="base-head.jsp"%>
<title>Programas de Fidelidade</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		<%@include file="nav-menu.jsp"%>

		<div class="page-header">
			<h1>Programas de Fidelidade</h1>
		</div>

		<c:if test="${not empty sessionScope.success}">
			<div class="alert alert-success alert-dismissible fade in">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					${sessionScope.success}
			</div>
		</c:if>

		<c:if test="${not empty sessionScope.error}">
			<div class="alert alert-danger alert-dismissible fade in">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					${sessionScope.error}
			</div>
		</c:if>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Lista de Programas de Fidelidade</h3>
			</div>
			<div class="panel-body">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome do Programa</th>
							<th>Milhas Acumuladas</th>
							<th>Status</th>
							<th>Data de Validade</th>
							<th>Companhia</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${loyaltyPrograms}" var="lp">
							<tr>
								<td>${lp.id}</td>
								<td>${lp.nomePrograma}</td>
								<td>${lp.milhasAcumuladas}</td>
								<td>${lp.status}</td>
								<td><fmt:formatDate value="${lp.dataValidade}" pattern="dd/MM/yyyy" /></td>
								<td>${lp.companhia.name}</td>
								<td>
									<a href="loyaltyprogram/update?loyaltyProgramId=${lp.id}" class="btn btn-primary btn-sm">Editar</a>
									<a href="loyaltyprogram/delete?id=${lp.id}" class="btn btn-danger btn-sm">Excluir</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="panel-footer">
				<a href="loyaltyprogram/form" class="btn btn-success">Novo Programa de Fidelidade</a>
			</div>
		</div>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

