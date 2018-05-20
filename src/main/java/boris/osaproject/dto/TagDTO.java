package boris.osaproject.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import boris.osaproject.entity.Tag;

public class TagDTO implements Serializable {

	public static Set<TagDTO> convert(Set<Tag> tagEntitySet) {
		Set<TagDTO> tagDTOSet = new HashSet<>();
		Iterator<Tag> iterator = tagEntitySet.iterator();
		while (iterator.hasNext()) {
			Tag tag = (Tag) iterator.next();
			TagDTO tagDTO = new TagDTO(tag);
			tagDTOSet.add(tagDTO);
		}
		return tagDTOSet;
	}

	private String name;

	public TagDTO() {

	}

	private TagDTO(String name) {
		this.name = name;
	}

	public TagDTO(Tag tag) {
		this(tag.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
