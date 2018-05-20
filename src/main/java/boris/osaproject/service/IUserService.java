package boris.osaproject.service;

import java.util.Set;

import boris.osaproject.entity.User;

public interface IUserService {

	User findOne(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByEmailAndPassword(String email, String password);

	Set<User> findAll();

	User save(User user);

	void delete(String username);
}
