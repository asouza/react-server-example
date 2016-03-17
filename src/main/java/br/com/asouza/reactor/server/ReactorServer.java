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

public class ReactorServer {

	private static ExecutorService threadRequestsPool = Executors.newFixedThreadPool(32);

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			Socket newClient = server.accept();
			PrintStream response = new PrintStream(newClient.getOutputStream());
			Action action = discoverAction(newClient);

			threadRequestsPool.execute(() -> {
				action.execute(response);
				closeResourcesAfterLogic(newClient, response);
			});
		}
	}

	private static Action discoverAction(Socket newClient) throws IOException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
		InputStream fromClient = newClient.getInputStream();
		try (Scanner scanner = new Scanner(fromClient)) {

			Action action = (Action) Class.forName(
					"br.com.asouza.reactor.actions." + scanner.next()).newInstance();
			return action;
		}
	}

	private static void closeResourcesAfterLogic(Socket newClient, PrintStream response) {
		try {
			response.close();
			newClient.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
