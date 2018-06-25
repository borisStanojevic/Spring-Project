package boris.osaproject.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails{
	
	@Id
	@Column(name = "user_username", unique = true, nullable = false)
	private String username;

	@Column(name = "user_password", unique = false, nullable = false)
	private String password;

	@Column(name = "user_email", unique = false, nullable = false)
	private String email;

	@Column(name = "user_full_name", unique = false, nullable = false)
	private String fullName;

	@Column(name = "user_photo", unique = false, nullable = false)
	private String photo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Post> posts;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Comment> comments;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE } , fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_username"), inverseJoinColumns = @JoinColumn(name = "role_role"))
	private Set<Role> roles;

	@PrePersist
	void preInsert() {
		if (photo == null || "".equals(photo))
			photo = "default_user_photo.png";
		if(fullName == null || "".equals(fullName))
			fullName = "";
		}
	
	public void addRole(Role role)
	{
		roles.add(role);
		role.getUsers().add(this);
	}
	
	public void removeRole(Role role)
	{
		roles.remove(role);
		role.getUsers().remove(this);
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
		roles = new HashSet<>();
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
	
	public Set<Role> getRoles() {
		return roles;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
