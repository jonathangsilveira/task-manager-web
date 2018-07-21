package edu.jonathangs.practice.taskmanager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransacaoImpl implements Transacao {
	private Connection conexao;
	
	public TransacaoImpl(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void iniciarTransacao() throws SQLException {
		if (conexao.getAutoCommit()) {
			conexao.setAutoCommit(false);
		}
	}

	@Override
	public void abortarTransacao() throws SQLException {
		conexao.rollback();
	}

	@Override
	public void finalizarTransacao() throws SQLException {
		conexao.commit();
	}
	
	protected Connection getConexao() {
		return conexao;
	}
	
	protected void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	
	protected void encerrarOperacoes(Statement stm, ResultSet rs) {
		if (stm != null)
			try {
				stm.close();
			} catch (SQLException e) { }
		
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) { }
	}

}
