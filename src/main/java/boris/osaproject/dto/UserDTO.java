package boris.osaproject.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import boris.osaproject.entity.User;

public class UserDTO implements Serializable {

	public static Set<UserDTO> convert(Set<User> userEntitySet) {
		Set<UserDTO> userDTOSet = new HashSet<>();
		Iterator<User> iterator = userEntitySet.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			UserDTO userDTO = new UserDTO(user);
			userDTOSet.add(userDTO);
		}
		return userDTOSet;
	}

	private String username;
	private String password;
	private String email;
	private String fullName;

	public UserDTO() {
	}

	private UserDTO(String username, String password, String email, String fullName) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
	}

	public UserDTO(User user) {
		this(user.getUsername(), user.getPassword(), user.getEmail(), user.getFullName());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
