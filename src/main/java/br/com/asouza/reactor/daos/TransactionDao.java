package br.com.asouza.reactor.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import reactor.core.publisher.Flux;
import br.com.asouza.reactor.models.Transaction;
import br.com.asouza.reactor.transformers.TransactionTransformer;

public class TransactionDao {

	private Connection connection;

	public TransactionDao(Connection connection) {
		this.connection = connection;
	}

	public Flux<Transaction> list(int count) {
		try {
			PreparedStatement ps = connection.prepareStatement("select * from transacao limit ?");
			ps.setInt(1, count);
			ResultSet resultSet = ps.executeQuery();
			return Flux.fromIterable(new ResultSetIterator<Transaction>(resultSet,new TransactionTransformer()));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
