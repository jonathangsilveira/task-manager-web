package edu.jonathangs.practice.taskmanager.dao;

import java.sql.SQLException;

public interface Transacao {
	void iniciarTransacao() throws SQLException;
	void abortarTransacao() throws SQLException;
	void finalizarTransacao() throws SQLException;
}
