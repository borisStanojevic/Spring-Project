package boris.osaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boris.osaproject.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
