package br.com.asouza.reactor.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import reactor.core.publisher.SchedulerGroup;
import br.com.asouza.reactor.actions.Action;
import br.com.asouza.reactor.actions.IndexedRunnable;

public class ReactorServer {

	private static SchedulerGroup async = SchedulerGroup.async();
	private static SchedulerGroup io = SchedulerGroup.io();

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			Socket newClient = server.accept();
			PrintStream response = new PrintStream(newClient.getOutputStream());
			Action action = discoverAction(newClient);
			
			Runnable runnable = () -> {
				IndexedRunnable actionRunnable = action.execute(response);
				actionRunnable.run();
				
				System.out.println("Closing resources "+actionRunnable.toString());
				closeResourcesAfterLogic(newClient, response);
			};
			if (action.hasDatabaseCalls()) {
				io.accept(runnable);
			} else {
				async.accept(runnable);
			}
		}
	}

	private static Action discoverAction(Socket newClient) throws IOException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
		InputStream fromClient = newClient.getInputStream();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(fromClient);

		Action action = (Action) Class.forName("br.com.asouza.reactor.actions." + scanner.next())
				.newInstance();
		return action;
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
