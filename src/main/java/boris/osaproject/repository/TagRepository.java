package boris.osaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boris.osaproject.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, String> {

	@Query(value = "SELECT * FROM tags WHERE tag_name IN (SELECT post_tag.tag_name FROM post_tag WHERE post_tag.post_id = ?1)", nativeQuery = true)
	List<Tag> findAll(Integer postId);
	
	@Query(value = "DELETE FROM post_tag WHERE post_id = ?1 AND tag_name = ?2", nativeQuery=true)
	void delete(Integer postId, String tagName);
	
}
