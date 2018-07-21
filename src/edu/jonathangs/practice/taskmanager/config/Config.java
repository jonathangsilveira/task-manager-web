package edu.jonathangs.practice.taskmanager.config;

public abstract class Config{
	// Driver JDBC
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 

	// URL do servidor MYSQL
	public static final String SERVIDOR = "jdbc:mysql://localhost";

	// Nome da base
	public static final String BASE  = "tasks_manager";

	// Credenciais
	public static final String USER = "root";
	public static final String PASS = "2018";
	
	public static final String TIMEZONE_PARAMETERS = "useTimezone=true&serverTimezone=UTC";

}
