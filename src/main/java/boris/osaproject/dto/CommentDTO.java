package boris.osaproject.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import boris.osaproject.entity.Comment;

public class CommentDTO implements Serializable {

	public static Set<CommentDTO> convert(Set<Comment> commentEntitySet) {
		Set<CommentDTO> commentDTOSet = new HashSet<>();
		Iterator<Comment> iterator = commentEntitySet.iterator();
		while (iterator.hasNext()) {
			Comment comment = (Comment) iterator.next();
			CommentDTO commentDTO = new CommentDTO(comment);
			commentDTOSet.add(commentDTO);
		}
		return commentDTOSet;
	}

	private Integer id;
	private String content;
	private UserDTO author;
	private Date date;
	private Integer likes;
	private Integer dislikes;

	public CommentDTO() {

	}

	private CommentDTO(Integer id, String content, UserDTO author, Date date, Integer likes, Integer dislikes) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.date = date;
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public CommentDTO(Comment c) {
		this(c.getId(), c.getContent(), new UserDTO(c.getAuthor()), c.getDate(), c.getLikes(), c.getDislikes());
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

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
