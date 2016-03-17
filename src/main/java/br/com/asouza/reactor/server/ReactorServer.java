package br.com.asouza.reactor.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.asouza.reactor.actions.ListAction;

public class ReactorServer {

	private static ExecutorService threadRequestsPool = Executors.newFixedThreadPool(32);

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			Socket newClient = server.accept();
			PrintStream response = new PrintStream(newClient.getOutputStream());

			threadRequestsPool.execute(() -> {
				// Spring will decide which method should respond to the request
					new ListAction().execute(response);
					closeResourcesAfterLogic(newClient, response);
				});
		}
	}

	private static void closeResourcesAfterLogic(Socket newClient, PrintStream response) {
		response.close();
		try {
			newClient.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
