package br.com.asouza.reactor.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import reactor.core.publisher.Flux;
import br.com.asouza.reactor.models.Transaction;
import br.com.asouza.reactor.transformers.TransactionTransformer;

public class HalfReactiveTransactionDao implements TransactionDao {

	private Connection connection;

	public HalfReactiveTransactionDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Flux<Transaction> list(int count) {
		try {
			// #blocking :(
			PreparedStatement ps = connection.prepareStatement("select * from transacao limit ?");
			ps.setInt(1, count);
			ResultSet rs = ps.executeQuery();

			// #nonblocking, but it is too late
			return Flux.fromIterable(new ResultSetIterator<Transaction>(rs,
					new TransactionTransformer()));
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

}
