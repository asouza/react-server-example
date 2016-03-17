package br.com.asouza.reactor.actions;

import java.io.PrintStream;

public class JustInMemoryAction implements Action{

	public void execute(PrintStream response) {
		System.out.println("executando logica " + Thread.currentThread().getName());
		StringBuilder html = new StringBuilder("<html><body>");
		response.print(html.append("Simple page").append("</body></html>"));

	}
}
