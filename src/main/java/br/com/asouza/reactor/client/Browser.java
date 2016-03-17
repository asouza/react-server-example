package br.com.asouza.reactor.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Browser {

	public static void main(String[] args) throws UnknownHostException, IOException {
			try (Socket browser = new Socket("localhost", 8080)) {
				handleServerReturn(browser);
			}

	}

	private static void handleServerReturn(Socket browser) throws IOException {
		try (InputStream serverResponse = browser.getInputStream();
				Scanner scanner = new Scanner(serverResponse)) {
			while (scanner.hasNext()) {
				System.out.println(scanner.next());
			}
		}
	}
}
