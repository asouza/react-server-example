package br.com.asouza.reactor.actions;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
	public void execute(PrintStream response) {
		System.out.println("list action " + Thread.currentThread().getName());

		StringBuilder html = new StringBuilder("<html><body>");
		try (Connection connection = ConnectionFactory.get()) {
			List<Transaction> txs = new TransactionDao(connection).list();
			System.out.println(txs.size()+"====");
			response.println(html.append(txs.size()).append("</body></html>"));			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
