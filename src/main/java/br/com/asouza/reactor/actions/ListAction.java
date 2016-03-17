package br.com.asouza.reactor.actions;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import br.com.asouza.reactor.daos.UserDao;
import br.com.asouza.reactor.models.User;

public class ListAction implements Action {

	/* (non-Javadoc)
	 * @see br.com.asouza.reactor.actions.Action#execute(java.io.PrintStream)
	 */
	@Override
	public void execute(PrintStream response) {
		System.out.println("executando logica "+Thread.currentThread().getName());
		StringBuilder html = new StringBuilder("<html><body><table>");
		List<User> users = new UserDao().list();
		String rows = users.stream().map(u -> "<tr><td>" + u.getName() + "</td></tr>")
				.collect(Collectors.joining());
		response.print(html.append(rows).append("</table></body></html>"));

	}

}
