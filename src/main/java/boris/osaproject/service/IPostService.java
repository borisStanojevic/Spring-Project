package boris.osaproject.service;

import java.util.Set;

import boris.osaproject.entity.Post;

public interface IPostService {
	
	Post findOne(Integer id);
	
	Set<Post> findAll();
	
	Post save(Post post);
	
	void delete(Integer id);

}
