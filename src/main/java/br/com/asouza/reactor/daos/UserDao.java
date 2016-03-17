package br.com.asouza.reactor.daos;

import java.util.Arrays;
import java.util.List;

import br.com.asouza.reactor.models.User;

public class UserDao {

	public List<User> list() {
		return Arrays.asList(new User("alberto"),new User("alberto II"),new User("alberto III"));
	}

}
