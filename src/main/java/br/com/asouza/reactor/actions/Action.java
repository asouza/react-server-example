package br.com.asouza.reactor.actions;

import java.io.PrintStream;

public interface Action {

	public abstract IndexedRunnable execute(PrintStream response);
	
	default boolean hasDatabaseCalls() {
		return true;
	}

}