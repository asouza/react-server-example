package br.com.asouza.reactor.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.asouza.reactor.models.Transaction;

public class TransactionDao {

	private Connection connection;

	public TransactionDao(Connection connection) {
		this.connection = connection;
	}

	public List<Transaction> list(int count) {
		try {
			PreparedStatement ps = connection.prepareStatement("select * from transacao limit ?");
			ps.setInt(1, count);
			ResultSet resultSet = ps.executeQuery();
			ArrayList<Transaction> txs = new ArrayList<Transaction>();
			while(resultSet.next()){
				txs.add(new Transaction(resultSet.getBigDecimal("valor")));
			}			
			ps.close();
			return txs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
