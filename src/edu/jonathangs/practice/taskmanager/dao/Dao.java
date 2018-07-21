package edu.jonathangs.practice.taskmanager.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

	boolean inserir(T entidade) throws SQLException;
	
	boolean alterar(T entidade) throws SQLException;
	
	boolean excluir(int codigo) throws SQLException;

	T consultar(int codigo) throws SQLException;

	List<T> consultar() throws SQLException;
	
}
