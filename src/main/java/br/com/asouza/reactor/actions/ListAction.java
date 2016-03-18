package br.com.asouza.reactor.actions;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import reactor.core.publisher.Flux;
import br.com.asouza.reactor.daos.ConnectionFactory;
import br.com.asouza.reactor.daos.TransactionDao;
import br.com.asouza.reactor.models.Transaction;

public class ListAction implements Action {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.asouza.reactor.actions.Action#execute(java.io.PrintStream)
	 */
	@Override
	public Runnable execute(PrintStream response) {
		System.out.println("list action " + Thread.currentThread().getName());

		try (Connection connection = ConnectionFactory.get()) {
			Flux<Transaction> txs = new TransactionDao(connection).list(200000);
			//here we need to think about how to build the html in a reactive way
			return () -> response.println("<html><body>"+txs.count().get()+"</body></html>");
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
