package boris.osaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.dto.UserDTO;
import boris.osaproject.entity.Role;
import boris.osaproject.entity.User;
import boris.osaproject.service.IRoleService;
import boris.osaproject.service.IUserService;

@RestController
@RequestMapping(value = "/register")
public class RegistrationController {
	
	private static final String INITIAL_REGISTRATION_ROLE = "ROLE_COMMENTATOR";
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO)
	{
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		Role initialRole = roleService.findOne(INITIAL_REGISTRATION_ROLE);
		user.getRoles().add(initialRole);
		
		user = userService.add(user);
		//Ako postoji korisnik sa takvim emailom ili usernameom user ce biti null
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		
		//U suprotnom registracija je uspjesna i vrati tog korisnika sa inicijalno postavljenom COMMENTATOR rolom
		userDTO = new UserDTO(user);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}
}
