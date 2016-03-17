package br.com.asouza.reactor.actions;

import java.io.PrintStream;

public interface Action {

	public abstract void execute(PrintStream response);

}