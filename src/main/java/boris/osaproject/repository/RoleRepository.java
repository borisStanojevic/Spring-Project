package boris.osaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boris.osaproject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	
	Role findOne(String role);
}
