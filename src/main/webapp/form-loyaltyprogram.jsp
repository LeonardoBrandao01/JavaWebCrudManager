<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
		<%@include file="base-head.jsp"%>
	</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty loyaltyProgram ? 'Atualizar' : 'Adicionar'} Programa de Fidelidade</h3>
		
		<form action="${pageContext.request.contextPath}/loyaltyprogram/${action}" 
			method="POST">
			
			<input type="hidden" value="${loyaltyProgram.getId()}" name="loyaltyProgramId">
			
			<div class="row">
				<div class="form-group col-md-6">
					<label for="nomePrograma">Nome do Programa</label>
					<input type="text" class="form-control" id="nomePrograma" name="nomePrograma" 
						   autofocus="autofocus" placeholder="Nome do Programa" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o nome do programa.')"
						   oninput="setCustomValidity('')"
						   value="${loyaltyProgram.getNomePrograma()}" />
				</div>
				
				<div class="form-group col-md-6">
					<label for="milhasAcumuladas">Milhas Acumuladas</label>
					<input type="number" class="form-control" id="milhasAcumuladas" name="milhasAcumuladas" 
						   placeholder="Milhas Acumuladas" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe as milhas acumuladas.')"
						   oninput="setCustomValidity('')" 
						   value="${loyaltyProgram.getMilhasAcumuladas()}"/>
				</div>				
			</div>
			
			<div class="row">
				<div class="form-group col-md-4">
					<label for="status">Status</label>
					<select id="status" class="form-control" name="status" 
						    required oninvalid="this.setCustomValidity('Por favor, selecione o status.')"
						    oninput="setCustomValidity('')">
					  
					  <option value="" ${not empty loyaltyProgram ? "" : 'selected'} >
					  	Selecione um status
					  </option>
					  
					  <option value="Ativo" ${loyaltyProgram.getStatus() == 'Ativo' ? 'selected' : ''}>Ativo</option>
					  <option value="Inativo" ${loyaltyProgram.getStatus() == 'Inativo' ? 'selected' : ''}>Inativo</option>
					</select>
				</div>
				
				<div class="form-group col-md-4">
					<label for="dataValidade">Data de Validade</label>
					<input type="date" class="form-control" id="dataValidade" name="dataValidade" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a data de validade.')"
						   oninput="setCustomValidity('')"
						   value="<fmt:formatDate value='${loyaltyProgram.getDataValidade()}' pattern='yyyy-MM-dd' />" />
				</div>
				
				<div class="form-group col-md-4">
					<label for="companhia">Companhia</label>
					<select id="companhia" class="form-control selectpicker" name="companhia" 
						    required oninvalid="this.setCustomValidity('Por favor, selecione uma companhia.')"
						    oninput="setCustomValidity('')">
					  
					  <option value="" ${not empty loyaltyProgram ? "" : 'selected'} >
					  	Selecione uma companhia
					  </option>
					  
					  <c:forEach var="company" items="${companies}">
					  	<option value="${company.getId()}" 
					  		${loyaltyProgram.getCompanhia().getId() == company.getId() ? 
					  			'selected' : ''}
					  	>
					  		${company.getName()}
					  	</option>	
					  </c:forEach>
					</select>
				</div>
			</div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					
					<a href="${pageContext.request.contextPath}/loyaltyprograms" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary">${not empty loyaltyProgram ? 'Atualizar' : 'Cadastrar' } Programa de Fidelidade</button>
				</div>
			</div>
		</form>
		
	</div>

</body>
</html>

