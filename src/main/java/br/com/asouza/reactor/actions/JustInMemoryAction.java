package br.com.asouza.reactor.actions;

import java.io.PrintStream;

public class JustInMemoryAction implements Action{

	public Runnable execute(PrintStream response) {
		System.out.println("in memory action " + Thread.currentThread().getName());
		StringBuilder html = new StringBuilder("<html><body>");
		return () -> response.println(html.append("Simple page").append("</body></html>").toString());

	}
}
