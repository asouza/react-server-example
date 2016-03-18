package br.com.asouza.reactor.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Adapter that should transform {@link ResultSet} into an Iterable of some domain object
 * @author alberto
 *
 * @param <T>
 */
public class ResultSetIterator<T> implements Iterable<T> {

	private ResultSet resultSet;
	private Function<ResultSet, T> transformer;

	public ResultSetIterator(ResultSet resultSet,Function<ResultSet, T> transformer) {
		this.resultSet = resultSet;
		this.transformer = transformer;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				try {
					return resultSet.next();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public T next() {
				return transformer.apply(resultSet);
			}
		};
	}

}
