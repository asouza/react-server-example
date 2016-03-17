package br.com.asouza.reactor.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection get(){
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/testehibernate","root","");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
