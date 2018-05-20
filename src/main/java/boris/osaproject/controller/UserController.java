package boris.osaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.dto.UserDTO;
import boris.osaproject.entity.User;
import boris.osaproject.service.IUserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping
	public ResponseEntity<Set<UserDTO>> getAll() {
		Set<User> users = userService.findAll();
		Set<UserDTO> usersDTO = UserDTO.convert(users);
		return new ResponseEntity<Set<UserDTO>>(usersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{username}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username) {
		User user = userService.findOne(username);
		if (user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);

	}

}
