package br.com.asouza.reactor.transformers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import br.com.asouza.reactor.models.Transaction;

public class TransactionTransformer implements Function<ResultSet, Transaction>{

	@Override
	public Transaction apply(ResultSet rs) {
		try {
			return new Transaction(rs.getBigDecimal("valor"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
