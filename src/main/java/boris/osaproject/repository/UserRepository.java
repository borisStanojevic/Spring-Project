package boris.osaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boris.osaproject.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);
}
