package boris.osaproject.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boris.osaproject.dto.UserDTO;
import boris.osaproject.entity.User;
import boris.osaproject.service.IUserService;


@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private ServletContext context;
	
	@Autowired
	private IUserService userService;

	@GetMapping
	public ResponseEntity<Set<UserDTO>> getAll()
	{
		Set<User> users = userService.findAll();
		Set<UserDTO> usersDTO = UserDTO.convert(users);
		return new ResponseEntity<Set<UserDTO>>(usersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{username}")
	public ResponseEntity<UserDTO> getById(@PathVariable("username") String username)
	{
		User user = userService.findOne(username);
		if (user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		UserDTO userDTO = new UserDTO(user);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> add(@RequestBody UserDTO userDTO)
	{
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFullName(userDTO.getFullName());
		user.setEmail(userDTO.getEmail());
		user = userService.add(user);
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO)
	{
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		
		user = userService.add(user);
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO)
	{
		User user = userService.findOne(userDTO.getUsername());
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		User updatedUser = new User();
		updatedUser.setUsername(userDTO.getUsername());
		updatedUser.setEmail(userDTO.getEmail());
		updatedUser.setPassword(userDTO.getPassword());
		updatedUser.setFullName(userDTO.getFullName());
		updatedUser.setPhoto(userDTO.getPhoto());
		
		user = userService.save(updatedUser);
		userDTO = new UserDTO(user);
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/upload", consumes="multipart/form-data")
	public ResponseEntity<UserDTO> uploadImage(@RequestPart(name = "file") MultipartFile file)
	{
		String photoName = file.getOriginalFilename();
		String username = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
		
		User user = userService.findOne(username);
		
		File imagesFolder = new File("src/main/resources/static/images/");
		if(!imagesFolder.exists())
			imagesFolder.mkdir();
		imagesFolder.setWritable(true);
		
		try (InputStream in = file.getInputStream();
			 OutputStream out = new FileOutputStream(new File("src/main/resources/static/images/" + photoName)))
		{
			int read = 0;
			byte[] buffer = new byte[1024];
			
			while((read = in.read(buffer)) != -1)
				out.write(buffer, 0 , read);
			
			user.setPhoto(photoName);
			user = userService.save(user);
			
			return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping(value = "/{username}")
	public ResponseEntity<Void> delete(@PathVariable("username") String username)
	{
		User user = userService.findOne(username);
		if(user == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		userService.delete(username);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	

}
