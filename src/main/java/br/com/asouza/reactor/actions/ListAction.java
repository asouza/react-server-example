package br.com.asouza.reactor.actions;

import java.io.PrintStream;
import java.sql.Connection;

import reactor.core.publisher.Flux;
import br.com.asouza.reactor.daos.ConnectionFactory;
import br.com.asouza.reactor.daos.TransactionDao;
import br.com.asouza.reactor.infra.GlobalCounter;
import br.com.asouza.reactor.models.Transaction;

public class ListAction implements Action {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.asouza.reactor.actions.Action#execute(java.io.PrintStream)
	 */
	@Override
	public IndexedRunnable execute(PrintStream response) {
		int index = GlobalCounter.get();
		System.out.println("list action("+index+") " + Thread.currentThread().getName());

			Connection connection = ConnectionFactory.get();
			Flux<Transaction> txs = new TransactionDao(connection).list(200000);
			
			txs.doOnComplete(() -> {
				try {
					connection.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			//here we need to think about how to build the html in a reactive way
			return new IndexedRunnable(index,() -> {
				System.out.println("gerando a resposta para "+index);
				response.println("<html><body>"+txs.count().get()+"</body></html>");
			});

	}

}
