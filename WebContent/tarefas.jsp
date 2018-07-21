<%@page import="edu.jonathangs.practice.taskmanager.controller.TarefaController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="./bootstrap/css/bootstrap.css" />
<script src="./bootstrap/js/jquery.min.js"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>
<title>Tarefas</title>
</head>
<body>

	<h1><%= TarefaController.getMensagemErro() %></h1>

	<!-- Título da tela -->
	<div class="col-sm-12 col-md-12">
		<div class="col-sm-4 col-md-4">&nbsp;</div>
		<div class="col-sm-4 col-md-4">
			<h3>Tarefas</h3>
		</div>
		<div class="col-sm-4 col-md-4">&nbsp;</div>
	</div>

	<!-- Link inclusão -->
	<div class="col-sm-11 col-md-11">
		<a href="#" data-toggle="modal" data-target="#inserirModal"> <span
			class="glyphicon glyphicon-plus"></span> Novo
		</a>
	</div>

	<!-- Espaçamento -->
	<div class="col-sm-12 col-md-12">&nbsp;</div>

	<!-- Tabela de informações -->
	<div class="col-sm-12 col-md-12">
	
		<jsp:useBean id="tarefas" class="edu.jonathangs.practice.taskmanager.controller.TarefaController"/>
	
		<table class="table table-hover table-striped">
			<tr>
				<th>#</th>
				<th width="15%">Título</th>
				<th width="30%">Descrição</th>
				<th width="10%">Status</th>
				<th width="15%">Criação</th>
				<th width="15%">Ultima alteração</th>
				<th width="15%">Conclusão</th>
				<th>Ações</th>
			</tr>
			<c:forEach var="tarefa" items="${tarefas.buscarTarefasVisiveis()}">
				<tr>
					<td>${ tarefa.getId() }</td>
					<td>${ tarefa.getTitulo() }</td>
					<td>${ tarefa.getDescricao() }</td>
					<td>${ tarefa.getDescricaoStatus() }</td>
					<td>${ tarefa.getInclusao() }</td>
					<td>${ tarefa.getAlteracao() }</td>
					<td>${ tarefa.getConclusao() }</td>
					<td>
						<c:if test="${ !tarefa.isConcluida() }">
							<a href="#" data-toggle="modal" data-target="#editarModal-${ tarefa.getId() }">
								<span class="glyphicon glyphicon-pencil"></span>
							</a>
							<a href="#" data-toggle="modal" data-target="#removerModal-${ tarefa.getId() }">
								<span class="glyphicon glyphicon-remove"></span>
							</a>
						</c:if>
					</td>
				</tr>
				<!--MODAL DO EXCLUIR -->
				<div class="modal fade" id="removerModal-${ tarefa.getId() }" tabindex="-1"
					role="dialog" aria-labelledby="removerModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="alert alert-warning">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4>Você está removendo uma Tarefa!</h4>
							<p>Este registro não será listado mais no sistema.</p>
							<br />
							<p>Deseja realmente remover esta Tarefa?</p>
							<br />
							<div class="text-right">
								<form action="./TarefaController" method="post">
									<input type="hidden" name="edId" value="${ tarefa.getId() }"> <input
										type="hidden" name="edTitulo" value="${ tarefa.getTitulo() }" />
									<button name="acao" value="delete" type="submit"
										class="btn btn-danger"
										formaction="./TarefaController?codigo=${ tarefa.getId() }">Excluir</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Cancelar</button>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!--MODAL DE EDIÇÃO -->
				<div class="modal fade" id="editarModal-${ tarefa.getId() }" tabindex="-1"
					role="dialog" aria-labelledby="#editarModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">Editar Tarefa</h4>
							</div>
	
							<form class="form-horizontal" role="form" method="POST"
								action="./TarefaController?codigo=${ tarefa.getId() }">
								<div class="modal-body">
									<div class="form-group">
										<label for="inputEdId" class="col-sm-2 control-label">
											Id: </label>
										<div class="col-sm-10">
											<input class="form-control" type="text" name="edId" value="${ tarefa.getId() }" disabled="disabled"/>
										</div>
									</div>
									<br />
									<div class="form-group">
										<label for="inputEdTitulo" class="col-sm-2 control-label">
											Título: </label>
										<div class="col-sm-10">
											<input class="form-control" type="text" name="edTitulo" value="${ tarefa.getTitulo() }" />
										</div>
									</div>
									<br />
									<div class="form-group">
										<label for="inputEdDescricao" class="col-sm-2 control-label">
											Descrição: </label>
										<div class="col-sm-10">
											<input class="form-control" type="text" name="edDescricao" value="${ tarefa.getDescricao() }" />
										</div>
									</div>
									<br />
									<div class="form-group">
										<label for="inputCkb" class="col-sm-2 control-label">
											Status: </label>
										<input class="form-control" type="checkbox" name="ckbConcluido"
												value="concluida" <c:if test="${ tarefa.isConcluida() }">checked</c:if>/>
									</div>
									<br />
								</div>
								<div class="modal-footer">
									<input type="hidden" name="acao" value="edit" />
									<button type="submit" class="btn btn-primary">
										<span class="glyphicon glyphicon-floppy-open"></span> Gravar
									</button>
									<button class="btn btn-default btn-md" data-dismiss="modal">
										Voltar</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</c:forEach>
		</table>
	</div>
	<!--MODAL DE INCLUSÃO -->
	<div class="modal fade" id="inserirModal" tabindex="-1" role="dialog"
		aria-labelledby="#inserirModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Nova Tarefa</h4>
				</div>

				<form class="form-horizontal" role="form" method="POST"
					action="./TarefaController">
					<div class="modal-body">
						<div class="form-group">
							<label for="inputEdTitulo" class="col-sm-2 control-label">
								Título: </label>
							<div class="col-sm-10">
								<input class="form-control" type="text" name="edTitulo" />
							</div>
						</div>
						<div class="form-group">
							<label for="inputEdDescricao" class="col-sm-2 control-label">
								Descrição: </label>
							<div class="col-sm-10">
								<input class="form-control" type="text" name="edDescricao" />
							</div>
						</div>						
					</div>
					<div class="modal-footer">
						<input type="hidden" name="acao" value="insert" />
						<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-floppy-open"></span> Gravar
						</button>
						<button class="btn btn-default btn-md" data-dismiss="modal">
							Voltar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Legenda -->
	<div class="col-md-12">
		<div class="col-md-2 col-sm-4 col-xs-4 pull-right"
			style="margin-top: 20px; padding: 10px; background-color: #f8f8f8; border: 1px solid #e7e7e7;">
			<h4>Legenda:</h4>
			<span class="glyphicon glyphicon-pencil"></span> 
			<small>-Editar</small> <br /> 
			<span class="glyphicon glyphicon-remove"></span> 
			<small>-Excluir</small> <br />
		</div>
	</div>
</body>
</html>