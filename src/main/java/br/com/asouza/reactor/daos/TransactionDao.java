package br.com.asouza.reactor.daos;

import reactor.core.publisher.Flux;
import br.com.asouza.reactor.models.Transaction;

public interface TransactionDao {

	public abstract Flux<Transaction> list(int count);

}