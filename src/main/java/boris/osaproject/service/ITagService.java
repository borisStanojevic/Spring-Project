package boris.osaproject.service;

import java.util.Set;

import boris.osaproject.entity.Tag;

public interface ITagService {

	Tag findOne(String name);

	Set<Tag> findAll(Integer postId);

	Tag save(Tag tag);

	void delete(Integer postId, String tagName);
}
