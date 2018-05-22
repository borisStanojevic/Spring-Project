package boris.osaproject.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag implements Serializable {
	
	@Id
	@Column(name = "tag_name", unique = true, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "tags")
	private Set<Post> posts;

	public Tag() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Post> getPosts() {
		return posts;
	}

}
