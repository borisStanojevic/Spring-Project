package boris.osaproject.service;

import java.util.Set;

import boris.osaproject.entity.Tag;

public interface ITagService {

	Tag findOne(Integer id);

	Set<Tag> findAll();

	Tag save(Tag tag);

	void delete(Integer id);
}
