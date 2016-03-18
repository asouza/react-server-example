package br.com.asouza.reactor.actions;

import java.io.PrintStream;
import java.util.function.Supplier;

public interface Action {

	public abstract Runnable execute(PrintStream response);

}