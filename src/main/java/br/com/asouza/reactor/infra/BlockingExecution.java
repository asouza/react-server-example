package br.com.asouza.reactor.infra;

public class BlockingExecution {

	public static void block(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
