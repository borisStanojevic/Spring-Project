package boris.osaproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boris.osaproject.entity.Tag;
import boris.osaproject.repository.TagRepository;

@Service
public class TagService implements ITagService {

	@Autowired
	TagRepository tagRepository;

	@Override
	public Tag findOne(String name) {
		return tagRepository.findOne(name);
	}

	@Override
	public Set<Tag> findAll(Integer postId) {
		return new HashSet<>(tagRepository.findAll(postId));
	}

	@Override
	public Tag save(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public void delete(Integer postId, String tagName) {
		tagRepository.delete(postId, tagName);
	}

}
