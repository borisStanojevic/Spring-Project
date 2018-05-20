package boris.osaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boris.osaproject.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
	
}
