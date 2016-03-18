package br.com.asouza.reactor.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import br.com.asouza.reactor.models.Transaction;
import br.com.asouza.reactor.transformers.TransactionTransformer;

public class TransactionDao {

	private Connection connection;

	public TransactionDao(Connection connection) {
		this.connection = connection;
	}

	public Flux<Transaction> list(int count) {
		//#nonblocking
		Mono<ResultSet> resultSet = Mono.fromCallable(() -> {
			PreparedStatement ps = connection.prepareStatement("select * from transacao limit ?");
			ps.setInt(1, count);
			return ps.executeQuery();
		});

		//#nonblocking
		return resultSet.flatMap(rs -> Flux.fromIterable(new ResultSetIterator<Transaction>(rs,
				new TransactionTransformer())));
	}

}
