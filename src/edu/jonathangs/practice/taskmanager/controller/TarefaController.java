package edu.jonathangs.practice.taskmanager.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jonathangs.practice.taskmanager.Converters;
import edu.jonathangs.practice.taskmanager.bd.ConexaoMysql;
import edu.jonathangs.practice.taskmanager.config.Config;
import edu.jonathangs.practice.taskmanager.dao.TarefaDao;
import edu.jonathangs.practice.taskmanager.model.Tarefa;
import edu.jonathangs.practice.taskmanager.model.TarefaDecorator;

/**
 * Servlet implementation class TasksController
 */
@WebServlet("/TarefaController")
public class TarefaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static String mMensagemErro = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TarefaController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		mMensagemErro = "";
		final String acao= request.getParameter("acao").toLowerCase();
		if (isDelete(acao)) {
			final String codigo = request.getParameter("codigo");
			excluir(Integer.valueOf(codigo));
		} else {
			final String titulo = request.getParameter("edTitulo");
			final String descricao = request.getParameter("edDescricao");
			Tarefa tarefa = new Tarefa();
			if (isInsert(acao)) {
				tarefa.setTitulo(titulo);
				tarefa.setDescricao(descricao);
				inserir(tarefa);
			} else {
				final String codigo = request.getParameter("codigo");
				final String valor = request.getParameter("ckbConcluido");
				int status = "concluida".equals(valor) ? 1 : 0;
				tarefa.setId(Integer.valueOf(codigo));
				tarefa.setTitulo(titulo);
				tarefa.setDescricao(descricao);
				tarefa.setStatus(status);
				alterar(tarefa);
			}
		}
		response.sendRedirect("./tarefas.jsp?concluido="+(Objects.nonNull(mMensagemErro)));
	}

	private boolean isInsert(final String acao) {
		return acao.equals("insert");
	}

	private boolean isDelete(final String acao) {
		return acao.equals("delete");
	}
	
	public List<TarefaDecorator> buscarTarefasVisiveis() {
		List<TarefaDecorator> tarefas = new ArrayList<>();
		try {
			ConexaoMysql.conectar(Config.USER, Config.PASS, Config.BASE);
			TarefaDao dao = new TarefaDao(ConexaoMysql.getConexao());
			List<Tarefa> resultado = dao.consultar();
			for (Tarefa tarefa : resultado) {
				tarefas.add(new TarefaDecorator(tarefa));
			}
		} catch (ClassNotFoundException | SQLException e) {
			mMensagemErro = String.format("Erro ao buscar Tarefas!\nCausa: %s", e.getMessage());
		} finally {
			ConexaoMysql.disconectar();
		}
		return tarefas;
	}
	
	private void inserir(Tarefa tarefa) {
		try {
			ConexaoMysql.conectar(Config.USER, Config.PASS, Config.BASE);
			TarefaDao dao = new TarefaDao(ConexaoMysql.getConexao());
			dao.inserirEmTransacao(tarefa);
		} catch (ClassNotFoundException | SQLException e) {
			mMensagemErro = String.format("Erro ao inserir Tarefa!\nCausa: %s", e.getMessage());
		} finally {
			ConexaoMysql.disconectar();
		}
	}
	
	private void alterar(Tarefa tarefa) {
		try {
			ConexaoMysql.conectar(Config.USER, Config.PASS, Config.BASE);
			TarefaDao dao = new TarefaDao(ConexaoMysql.getConexao());
			dao.alterarEmTransacao(tarefa);
		} catch (ClassNotFoundException | SQLException e) {
			mMensagemErro = String.format("Erro ao alterar Tarefa!\nCausa: %s", e.getMessage());
		} finally {
			ConexaoMysql.disconectar();
		}
	}
	
	private void excluir(int id) {
		try {
			ConexaoMysql.conectar(Config.USER, Config.PASS, Config.BASE);
			TarefaDao dao = new TarefaDao(ConexaoMysql.getConexao());
			dao.excluirEmTransacao(id);
		} catch (ClassNotFoundException | SQLException e) {
			mMensagemErro = String.format("Erro ao excluir Tarefa!\nCausa: %s", e.getMessage());
		} finally {
			ConexaoMysql.disconectar();
		}
	}
	
	public static String getMensagemErro() {
		return mMensagemErro;
	}
	
}
