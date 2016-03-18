package br.com.asouza.reactor.actions;

import java.io.PrintStream;

import br.com.asouza.reactor.infra.GlobalCounter;

public class JustInMemoryAction implements Action{

	public IndexedRunnable execute(PrintStream response) {
		int index = GlobalCounter.get();
		System.out.println("in memory action("+index+") " + Thread.currentThread().getName());
		StringBuilder html = new StringBuilder("<html><body>");
		return new IndexedRunnable(index,() -> {
			System.out.println("generating response in memory "+index);
			response.println(html.append("Simple page").append("</body></html>").toString());
		});

	}
	
	@Override
	public boolean hasDatabaseCalls() {
		return false;
	}
}
