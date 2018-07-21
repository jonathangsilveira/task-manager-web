package edu.jonathangs.practice.taskmanager.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.jonathangs.practice.taskmanager.config.Config;

public class ConexaoMysql {
	private static Connection conexao;
	
	public static Connection getConexao() {
		return conexao;
	}
	
	public static void conectar(String usuario, 
			String senha, 
			String base) throws SQLException, ClassNotFoundException {
		Class.forName(Config.JDBC_DRIVER);
		String url = String.format("%s/%s?%s", Config.SERVIDOR, base, Config.TIMEZONE_PARAMETERS);
		conexao = DriverManager.getConnection(url, usuario, senha);
	}
	
	public static boolean isConexaoAberta() {
		boolean isAberta = false;
		if (conexao != null)
			try {
				isAberta = !conexao.isClosed();
			} catch (SQLException e) {
				isAberta = false;
			}
		return isAberta;
	}
	
	public static void disconectar() {
		if (isConexaoAberta()) {
			try {
				conexao.close();
			} catch (SQLException e) {}
		}
		conexao = null;
	}
	
	public static boolean isLoginValido(String usuario, 
			String senha, 
			String base) {
		Connection conexaoAux = null;
		boolean isValida = false;
		try {
			Class.forName(Config.JDBC_DRIVER);
			String url = String.format("%s/%s?%s", Config.SERVIDOR, base, Config.TIMEZONE_PARAMETERS);
			conexaoAux = DriverManager.getConnection(url, usuario, senha);
			isValida = true;
		} catch (Exception e) {
			isValida = false;
		} finally {
			try {
				if (conexaoAux != null)
					conexaoAux.close();
			} catch (SQLException sqlExc) {}
		}
		return isValida;
	}
	
}
