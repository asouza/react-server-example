package br.com.asouza.reactor.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Browser {

	public static void main(String[] args) throws UnknownHostException, IOException {
		for(int i = 0;i<50;i++) {
			if(i % 3 == 0){
				newRequest(i,"JustInMemoryAction");
			} else {
				newRequest(i,"ListAction");
			}
		}

	}

	private static void newRequest(int i, String action) {
			try (Socket browser = new Socket("localhost", 8080)) {
				OutputStream out = browser.getOutputStream();
				PrintStream ps = new PrintStream(out);
				ps.println(action);
				ps.flush();
				handleServerReturn(browser,i);
			} catch(Exception e){
				throw new RuntimeException(e);
			}
	}

	private static void handleServerReturn(Socket browser, int clientId) throws IOException {
		try (InputStream serverResponse = browser.getInputStream();
				Scanner scanner = new Scanner(serverResponse)) {
			System.out.println("Client "+clientId+"");
			while (scanner.hasNext()) {
				System.out.println(scanner.next());
			}
		}
	}
}
