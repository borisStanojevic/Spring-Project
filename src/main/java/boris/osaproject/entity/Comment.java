package boris.osaproject.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer id;

	@Column(name = "comment_content", unique = false, nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "comment_author_username", referencedColumnName = "user_username", nullable = false)
	private User author;

	@Temporal(TemporalType.DATE)
	@Column(name = "comment_date_posted", unique = false, nullable = false)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "comment_post_id", referencedColumnName = "post_id", nullable = false)
	private Post post;

	@Column(name = "comment_likes", unique = false, nullable = false)
	private Integer likes;

	@Column(name = "comment_dislikes", unique = false, nullable = false)
	private Integer dislikes;

	{
		post = new Post();
		likes = 0;
		dislikes = 0;
	}

	public Comment() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Post getPost() {
		return post;
	}
	
	public void setPost(Post post) {
		this.post = post;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Comment [id=%s, content=%s, author=%s, date=%s, post=%s, likes=%s, dislikes=%s]", id,
				content, author.getUsername(), date, post.getId(), likes, dislikes);
	}

}
