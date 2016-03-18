package br.com.asouza.reactor.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Browser {

	public static void main(String[] args) throws UnknownHostException, IOException {
		for (int i = 0; i < 50; i++) {
			if (i > 32) {
				newRequest(i, "JustInMemoryAction");
			} else {
				newRequest(i, "ListAction");
			}
		}

	}

	private static void newRequest(int i, String action) {
		try{
			Socket browser = new Socket("localhost", 8080);
			notifyWhichAction(action, browser);
			new Thread(() -> {
				handleServerReturn(browser, i);					
			}).start();
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

	private static void handleServerReturn(Socket browser, int clientId) {
		try(Scanner scanner = new Scanner(browser.getInputStream())) {
			while (scanner.hasNextLine()) {
				System.out.println("Client " + clientId);
				System.out.println(scanner.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
