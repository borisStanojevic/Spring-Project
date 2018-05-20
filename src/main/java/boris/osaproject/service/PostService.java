package boris.osaproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boris.osaproject.entity.Post;
import boris.osaproject.repository.PostRepository;

@Service
public class PostService implements IPostService {

	@Autowired
	PostRepository postRepository;

	@Override
	public Post findOne(Integer id) {
		return postRepository.findOne(id);
	}

	@Override
	public Set<Post> findAll() {
		return new HashSet<>(postRepository.findAll());
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void delete(Integer id) {
		postRepository.delete(id);
	}

}
