package br.com.asouza.reactor.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.asouza.reactor.models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.subscriber.ConsumerSubscriber;

public class ReactorTransactionDaoTest {

	private Connection connection;
	private TransactionDao transactionDao;

	@Before
	public void setup() {
		connection = ConnectionFactory.get();
		transactionDao = new TransactionDao(connection);
	}

	@After
	public void release() throws SQLException {
		connection.close();
	}

	@Test
	public void shouldListWithFluxToUnderstandHowItWorks() throws Exception {
		Flux<Transaction> transactions = transactionDao.list(100);
		
		Consumer<Transaction> onNextConsumer = (t) -> {
			BigDecimal lastTransaction = new BigDecimal("9458.45");
			if(t.getValue().compareTo(lastTransaction) == 0){
				throw new RuntimeException("Throwing exception just to test");
			}
			System.out.println(t.getValue());			
		};

		Consumer<Throwable> onErrorConsumer = (e) -> {
			System.out.println("Error " + e.getMessage());
		};

		Runnable onFinishConsumer = () -> {
			System.out.println("Acabou!");
		};
		
		ConsumerSubscriber<Transaction> consumer = new ConsumerSubscriber<Transaction>(
				onNextConsumer, onErrorConsumer, onFinishConsumer);
		
		transactions.subscribe(consumer);
	}
}
