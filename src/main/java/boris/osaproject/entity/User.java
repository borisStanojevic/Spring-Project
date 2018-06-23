package boris.osaproject.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
	@Column(name = "user_username", unique = true, nullable = false)
	private String username;

	@Column(name = "user_password", unique = false, nullable = false)
	private String password;

	@Column(name = "user_email", unique = true, nullable = false)
	private String email;

	@Column(name = "user_full_name", unique = false, nullable = false)
	private String fullName;

	@Column(name = "user_photo", unique = false, nullable = false)
	private String photo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Post> posts;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Comment> comments;

	@PrePersist
	void preInsert() {
		if (photo == null || "".equals(photo))
			photo = "default_user_photo.png";
		if(fullName == null || "".equals(fullName))
			fullName = "";
	}
	
	public void addPost(Post post)
	{
		posts.add(post);
		post.setAuthor(this);
	}
	
	public void removePost(Post post)
	{
		post.setAuthor(null);
		posts.remove(post);
	}

	{
		posts = new HashSet<>();
		comments = new HashSet<>();
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("User [username=%s, email=%s]", username, email);
	}

}
