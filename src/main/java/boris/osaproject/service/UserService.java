package boris.osaproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boris.osaproject.entity.User;
import boris.osaproject.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User findOne(String username) {
		return userRepository.findOne(username);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		User user = userRepository.findOne(username);
		if (user != null && user.getPassword().equals(password))
			return user;
		return null;
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && user.getPassword().equals(password))
			return user;
		return null;
	}

	@Override
	public Set<User> findAll() {
		return new HashSet<>(userRepository.findAll());
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void delete(String username) {
		userRepository.delete(username);
	}

}
