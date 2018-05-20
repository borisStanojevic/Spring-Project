package boris.osaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boris.osaproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query(value = "SELECT * FROM comments WHERE comment_post_id = ?1", nativeQuery = true)
	List<Comment> findAll(Integer postId);
}
