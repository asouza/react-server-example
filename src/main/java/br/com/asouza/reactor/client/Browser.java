package br.com.asouza.reactor.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Browser {

	public static void main(String[] args) throws UnknownHostException, IOException {
		for (int i = 1; i < 2; i++) {
			if (i % 3 == 0) {
				newRequest(i, "JustInMemoryAction");
			} else {
				newRequest(i, "ListAction");
			}
		}

	}

	private static void newRequest(int i, String action) {
		try (Socket browser = new Socket("localhost", 8080);Scanner scanner = new Scanner(browser.getInputStream())) {
			notifyWhichAction(action, browser);
			handleServerReturn(browser, i,scanner);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void notifyWhichAction(String action, Socket browser) throws IOException {
		OutputStream out = browser.getOutputStream();
		PrintStream ps = new PrintStream(out);
		ps.println(action);
		ps.flush();
	}

	private static void handleServerReturn(Socket browser, int clientId, Scanner scanner ) {
			System.out.println("Client " + clientId + " " + System.nanoTime());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());			
		}
	}
}
