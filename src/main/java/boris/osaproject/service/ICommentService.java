package boris.osaproject.service;

import java.util.Set;

import boris.osaproject.entity.Comment;

public interface ICommentService {
	
	Comment findOne(Integer id);
	
	Set<Comment> findAll(Integer postId);
	
	Comment save(Comment comment);
	
	void delete(Integer id);
}
