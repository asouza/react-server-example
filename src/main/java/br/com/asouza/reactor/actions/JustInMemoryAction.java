package br.com.asouza.reactor.actions;

import java.io.PrintStream;
import java.util.function.Supplier;

public class JustInMemoryAction implements Action{

	public Supplier<String> execute(PrintStream response) {
		System.out.println("in memory action " + Thread.currentThread().getName());
		StringBuilder html = new StringBuilder("<html><body>");
		return () -> html.append("Simple page").append("</body></html>").toString();

	}
}
