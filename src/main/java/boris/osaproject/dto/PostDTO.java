package boris.osaproject.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import boris.osaproject.entity.Post;

public class PostDTO implements Serializable {


	private Integer id;
	private String title;
	private String content;
	private String photo;
	private Date date;
	private String location;
	private Integer likes;
	private Integer dislikes;
	private UserDTO author;
	private Set<TagDTO> tags;
	private Set<CommentDTO> comments;

	public PostDTO() {

	}

	private PostDTO(Integer id, String title, String content, String photo, Date date, String location, Integer likes,
			Integer dislikes, UserDTO author, Set<TagDTO> tags, Set<CommentDTO> comments) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.photo = photo;
		this.date = date;
		this.location = location;
		this.likes = likes;
		this.dislikes = dislikes;
		this.author = author;
		this.tags = tags;
		this.comments = comments;
	}

	public PostDTO(Post p) {
		this(p.getId(), p.getTitle(), p.getContent(), p.getPhoto(), p.getDate(), p.getLocation(), p.getLikes(),
				p.getDislikes(), new UserDTO(p.getAuthor()), TagDTO.convert(p.getTags()),
				CommentDTO.convert(p.getComments()));
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public Set<TagDTO> getTags() {
		return tags;
	}

	public void setTags(Set<TagDTO> tags) {
		this.tags = tags;
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

}
