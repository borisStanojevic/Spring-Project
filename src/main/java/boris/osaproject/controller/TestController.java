//package boris.osaproject.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import boris.osaproject.dto.UserDTO;
//import boris.osaproject.entity.User;
//
//@RestController(value = "/api/test")
//public class TestController {
//
//	private static final UserDTO testUserDTO;
//	private static final String testJSON = "{\"success\":true,\"users\":[{\"id\":\"1\",\"name\":\"Petar Petrovic\",\"photo\":\"https://eliaslealblog.files.wordpress.com/2014/03/user-200.png\",\"username\":\"pekip\",\"password\":\"p123\"},{\"id\":\"2\",\"name\":\"Sava Savanovic\",\"photo\":\"https://eliaslealblog.files.wordpress.com/2014/03/user-200.png\",\"username\":\"savas\",\"password\":\"s456\"}]}";
//	
//	static {
//		
//		User u = new User();
//		u.setEmail("tarmiricmi123@grand.com");
//		u.setUsername("tarmiricmi123");
//		u.setPassword("123");
//		u.setFullName("Mitar Miric");
//
//		testUserDTO = new UserDTO(u);
//	}
//
//	@GetMapping
//	public ResponseEntity<UserDTO> getTestUser() {
//		return new ResponseEntity<UserDTO>(testUserDTO, HttpStatus.OK);
//	}
//}
