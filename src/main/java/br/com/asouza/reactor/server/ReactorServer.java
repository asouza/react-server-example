package br.com.asouza.reactor.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.asouza.reactor.actions.Action;
import br.com.asouza.reactor.actions.ListAction;

public class ReactorServer {

	private static ExecutorService threadRequestsPool = Executors.newFixedThreadPool(32);

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			Socket newClient = server.accept();
			PrintStream response = new PrintStream(newClient.getOutputStream());
			InputStream fromClient = newClient.getInputStream();
			Scanner scanner = new Scanner(fromClient);
			
			Action action = (Action) Class.forName("br.com.asouza.reactor.actions."+scanner.next()).newInstance();			
			
			threadRequestsPool.execute(() -> {
				// Spring will decide which method should respond to the request
					action.execute(response);
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
