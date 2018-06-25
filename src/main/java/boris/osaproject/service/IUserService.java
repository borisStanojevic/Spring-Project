package boris.osaproject.service;

import java.util.Set;

import boris.osaproject.entity.User;

public interface IUserService {

	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByEmailAndPassword(String email, String password);
	
	User add(User user);

	Set<User> findAll();

	User save(User user);

	void delete(String username);
}
