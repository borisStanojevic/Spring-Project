package boris.osaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import boris.osaproject.entity.User;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		System.out.println("Username iz custom details " + username);
		User myUser = userService.findByUsername(username);
		
        if (myUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return myUser;
        }
//		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(), myUser.getRoles());
//		return springUser;
	}
	
//	public User loadMyUserByUsername(String username)
//	{
//		return userService.findByUsername(username);
//	}

}
