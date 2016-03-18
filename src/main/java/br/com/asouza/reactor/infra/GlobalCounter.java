package br.com.asouza.reactor.infra;

import java.util.concurrent.atomic.AtomicInteger;

public class GlobalCounter {

	private static final AtomicInteger counter = new AtomicInteger();
	
	public static int get(){
		return counter.getAndIncrement();
	}
}
