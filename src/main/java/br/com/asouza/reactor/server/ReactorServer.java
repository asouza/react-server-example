package br.com.asouza.reactor.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.asouza.reactor.actions.ListAction;

public class ReactorServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8080);
		Socket newClient = server.accept();
		PrintStream response = new PrintStream(newClient.getOutputStream());
		
		new ListAction().execute(response);
	}
}
