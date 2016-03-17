package br.com.asouza.reactor.models;

import java.math.BigDecimal;

public class Transaction {

	private BigDecimal value;

	public Transaction(BigDecimal value) {
		this.value = value;
		// TODO Auto-generated constructor stub
	}
	
	public BigDecimal getValue() {
		return value;
	}

}
