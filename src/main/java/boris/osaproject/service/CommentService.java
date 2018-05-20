package boris.osaproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boris.osaproject.entity.Comment;
import boris.osaproject.repository.CommentRepository;

@Service
public class CommentService implements ICommentService {

	@Autowired
	CommentRepository commentRepository;

	@Override
	public Comment findOne(Integer id) {
		return commentRepository.findOne(id);
	}

	@Override
	public Set<Comment> findAll(Integer postId) {
		return new HashSet<>(commentRepository.findAll(postId));
	}

	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void delete(Integer id) {
		commentRepository.delete(id);
	}

}
