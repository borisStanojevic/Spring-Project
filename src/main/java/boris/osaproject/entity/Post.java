package boris.osaproject.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "posts")
public class Post implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "post_title", unique = false, nullable = false)
	private String title;

	@Column(name = "post_content", unique = false, nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "post_author_username", referencedColumnName = "user_username", nullable = false)
	private User author;

	@Column(name = "post_photo", unique = false, nullable = false)
	private String photo;

	@Column(name = "post_date_posted", unique = false, nullable = false)
	private String date;

	@Column(name = "post_location_latitude", unique = false, nullable = true)
	private Double locationLatitude;

	@Column(name = "post_location_longitude", unique = false, nullable = true)
	private Double locationLongitude;

	// Automatski ce da dobavi tagove koji se odnose na ovaj post
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_name"))
	private Set<Tag> tags;

	// Nece dobaviti komentare sve dok eskplicitno to ne zatrazimo
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
	private Set<Comment> comments;

	@Column(name = "post_likes", unique = false, nullable = false)
	private Integer likes;

	@Column(name = "post_dislikes", unique = false, nullable = false)
	private Integer dislikes;

	@PrePersist
	void preInsert() {
		if (photo == null || "".equals(photo))
			photo = "default.png";
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setPost(this);
	}

	public void removeComment(Comment comment) {
		comment.setPost(null);
		comments.remove(comment);
	}

	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getPosts().add(this);
	}

	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getPosts().remove(this);
	}

	{
		likes = 0;
		dislikes = 0;
		tags = new HashSet<>();
		comments = new HashSet<>();
	}

	public Post() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Double getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(Double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public Double getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(Double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Post [id=%s, title=%s, content=%s, date=%s, location=%s, likes=%s, dislikes=%s]", id,
				title, content, date, locationLatitude + "|" + locationLongitude, likes, dislikes);
	}

}
