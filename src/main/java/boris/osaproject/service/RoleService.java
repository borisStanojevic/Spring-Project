package boris.osaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boris.osaproject.entity.Role;
import boris.osaproject.repository.RoleRepository;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role findOne(String role) {
		return roleRepository.findOne(role);
	}
	
	
}
