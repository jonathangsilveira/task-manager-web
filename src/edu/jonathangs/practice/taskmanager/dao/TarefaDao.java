package edu.jonathangs.practice.taskmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.jonathangs.practice.taskmanager.Converters;
import edu.jonathangs.practice.taskmanager.model.Tarefa;
import edu.jonathangs.practice.taskmanager.model.TarefaHelper;

public class TarefaDao extends TransacaoImpl implements Dao<Tarefa> {
	
	private static final String INSERT_STATEMENT = "INSERT INTO tarefa (`titulo`, `descricao`, `status`, "
			+ "`data_inclusao`, `data_alteracao`, `data_conclusao`, `data_exclusao`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STATEMENT = "UPDATE tarefa SET titulo = ?, descricao = ?, status = ?, "
			+ "data_alteracao = ?, data_conclusao = ? WHERE id = ?";
	
	private static final String DELETE_STATEMENT = "UPDATE tarefa SET data_alteracao = ?, data_exclusao = ? WHERE id = ?";
	
	private static final String SELECT_ALL_STATEMENT = "SELECT id, titulo, descricao, status, data_inclusao, "
			+ "data_alteracao, data_conclusao, data_exclusao FROM tarefa";
	
	private static final String SELECT_BY_ID_STATEMENT = SELECT_ALL_STATEMENT + " WHERE id = ?";
	
	private static final String SELECT_BY_VISIBLE = SELECT_ALL_STATEMENT + " WHERE data_exclusao IS NULL";

	public TarefaDao(Connection conexao) {
		super(conexao);
	}

	@Override
	public boolean inserir(Tarefa entidade) throws SQLException {
		boolean inseriu = false;
		PreparedStatement pStm = null;
		ResultSet rs = null;
		try {
			pStm = getConexao().prepareStatement(INSERT_STATEMENT, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			pStm.setString(1, entidade.getTitulo());
			if (Objects.isNull(entidade.getDescricao())) {
				pStm.setNull(2, Types.NULL);
			} else {
				pStm.setString(2, entidade.getDescricao());
			}
			pStm.setInt(3, entidade.getStatus());
			entidade.setDataInclusao(new Date());
			pStm.setTimestamp(4, Converters.toTimestamp(entidade.getDataInclusao()));
			if (Objects.isNull(entidade.getDataAlteracao())) {
				pStm.setNull(5, Types.NULL);
			} else {
				pStm.setTimestamp(5, Converters.toTimestamp(entidade.getDataAlteracao()));
			}
			if (Objects.isNull(entidade.getDataConclusao())) {
				pStm.setNull(6, Types.NULL);
			} else {
				pStm.setTimestamp(6, Converters.toTimestamp(entidade.getDataConclusao()));
			}
			if (Objects.isNull(entidade.getDataExclusao())) {
				pStm.setNull(7, Types.NULL);
			} else {
				pStm.setTimestamp(7, Converters.toTimestamp(entidade.getDataExclusao()));
			}
			inseriu = pStm.executeUpdate() > 0;
			rs = pStm.getGeneratedKeys();
			if (rs != null && rs.next()) {
				entidade.setId(rs.getInt(1));
			}
		} finally {
			encerrarOperacoes(pStm, rs);
		}
		return inseriu;
	}

	@Override
	public boolean alterar(Tarefa entidade) throws SQLException {
		boolean alterou = false;
		PreparedStatement pStm = null;
		try {
			pStm = getConexao().prepareStatement(UPDATE_STATEMENT);
			pStm.setString(1, entidade.getTitulo());
			if (Objects.isNull(entidade.getDescricao())) {
				pStm.setNull(2, Types.NULL);
			} else {
				pStm.setString(2, entidade.getDescricao());
			}
			pStm.setInt(3, entidade.getStatus());
			Date dataAlteracao = new Date();
			entidade.setDataAlteracao(dataAlteracao);
			pStm.setTimestamp(4, Converters.toTimestamp(entidade.getDataAlteracao()));
			if (TarefaHelper.isConcluida(entidade)) {
				entidade.setDataConclusao(dataAlteracao);
			}
			if (Objects.isNull(entidade.getDataConclusao())) {
				pStm.setNull(5, Types.NULL);
			} else {
				pStm.setTimestamp(5, Converters.toTimestamp(entidade.getDataConclusao()));
			}
			pStm.setInt(6, entidade.getId());
			alterou = pStm.executeUpdate() > 0;
		} finally {
			encerrarOperacoes(pStm, null);
		}
		return alterou;
	}
	
	@Override
	public boolean excluir(int codigo) throws SQLException {
		boolean excluiu = false;
		PreparedStatement pStm = null;
		try {
			pStm = getConexao().prepareStatement(DELETE_STATEMENT);
			Date dataAlteracao = new Date();
			pStm.setTimestamp(1, Converters.toTimestamp(dataAlteracao));
			pStm.setTimestamp(2, Converters.toTimestamp(dataAlteracao));
			pStm.setInt(3, codigo);
			excluiu = pStm.executeUpdate() > 0;
		} finally {
			encerrarOperacoes(pStm, null);
		}
		return excluiu;
	}

	@Override
	public Tarefa consultar(int codigo) throws SQLException {
		PreparedStatement pStm = null;
		ResultSet rs = null;
		try {
			pStm = getConexao().prepareStatement(SELECT_BY_ID_STATEMENT);
			rs = pStm.executeQuery();
			if (Objects.nonNull(rs) && rs.next()) {
				return mapearTarefa(rs);
			}
		} finally {
			encerrarOperacoes(pStm, rs);
		}
		return null;
	}

	private Tarefa mapearTarefa(ResultSet rs) throws SQLException {
		Tarefa tarefa = new Tarefa();
		int id = rs.getInt(1);
		String titulo = rs.getString(2);
		String descricao = rs.getString(3);
		int status = rs.getInt(4);
		Timestamp dataInclusao = rs.getTimestamp(5);
		Timestamp dataAlteracao = rs.getTimestamp(6);
		Timestamp dataConclusao = rs.getTimestamp(7);
		Timestamp dataExclusao = rs.getTimestamp(8);
		tarefa.setId(id);
		tarefa.setTitulo(titulo);
		tarefa.setDescricao(descricao);
		tarefa.setStatus(status);
		tarefa.setDataInclusao(dataInclusao);
		tarefa.setDataAlteracao(dataAlteracao);
		tarefa.setDataConclusao(dataConclusao);
		tarefa.setDataExclusao(dataExclusao);
		return tarefa;
	}

	@Override
	public List<Tarefa> consultar() throws SQLException {
		List<Tarefa> tarefas = new ArrayList<>();
		PreparedStatement pStm = null;
		ResultSet rs = null;
		try {
			pStm = getConexao().prepareStatement(SELECT_BY_VISIBLE);
			rs = pStm.executeQuery();
			if (Objects.nonNull(rs)) {
				while (rs.next()) {
					tarefas.add(mapearTarefa(rs));
				}
			}
		} finally {
			encerrarOperacoes(pStm, rs);
		}
		return tarefas;
	}
	
	public void inserirEmTransacao(Tarefa entidade) throws SQLException {
		try {
			iniciarTransacao();
			inserir(entidade);
			finalizarTransacao();
		} catch (SQLException e) {
			abortarTransacao();
			throw e;
		}
	}
	
	public void alterarEmTransacao(Tarefa entidade) throws SQLException {
		try {
			iniciarTransacao();
			alterar(entidade);
			finalizarTransacao();
		} catch (SQLException e) {
			abortarTransacao();
			throw e;
		}
	}
	
	public void excluirEmTransacao(int id) throws SQLException {
		try {
			iniciarTransacao();
			excluir(id);
			finalizarTransacao();
		} catch (SQLException e) {
			abortarTransacao();
			throw e;
		}
	}

}
