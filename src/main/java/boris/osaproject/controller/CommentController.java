package boris.osaproject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.dto.CommentDTO;
import boris.osaproject.entity.Comment;
import boris.osaproject.entity.Post;
import boris.osaproject.entity.User;
import boris.osaproject.service.ICommentService;
import boris.osaproject.service.IPostService;
import boris.osaproject.service.IUserService;

@RestController
@RequestMapping(value = "/posts/{postId}/comments")
public class CommentController {

	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private IUserService userService;

	@GetMapping
	public ResponseEntity<Set<CommentDTO>> getAll(@PathVariable("postId") Integer postId) {
		
		Set<Comment> comments = commentService.findAll(postId);
		Set<CommentDTO> commentsDTO = CommentDTO.convert(comments);

		return new ResponseEntity<Set<CommentDTO>>(commentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<CommentDTO> getById(@PathVariable("id") Integer id)
	{
		Comment comment = commentService.findOne(id);
		if(comment != null)
		{
			CommentDTO commentDTO = new CommentDTO(comment);
			return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
		}
		return new ResponseEntity<CommentDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<CommentDTO> create(@PathVariable("postId") Integer postId, @RequestBody CommentDTO commentDTO)
	{
		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		comment.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		//Lajkovi i dislajkovi ce inicijalno bit nula posto ih ne postavljam
		User author = userService.findByUsername(commentDTO.getAuthor().getUsername());
		comment.setAuthor(author);
		Post post = postService.findOne(postId);
		comment.setPost(post);
		
		comment = commentService.save(comment);
		commentDTO = new CommentDTO(comment);
		
		return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.CREATED);
	}
	
	@PutMapping(consumes="application/json")
	public ResponseEntity<CommentDTO> update(@RequestBody CommentDTO commentDTO)
	{
		Comment comment = commentService.findOne(commentDTO.getId());
		if(comment == null)
			return new ResponseEntity<CommentDTO>(HttpStatus.BAD_REQUEST);
		
		Comment updatedComment = new Comment();
		updatedComment.setId(commentDTO.getId());
		updatedComment.setContent(commentDTO.getContent());
		updatedComment.setDate(commentDTO.getDate());
		updatedComment.setLikes(commentDTO.getLikes());
		updatedComment.setDislikes(commentDTO.getDislikes());
		updatedComment.getAuthor().setUsername(commentDTO.getAuthor().getUsername());
		updatedComment.getAuthor().setPassword(commentDTO.getAuthor().getPassword());
		updatedComment.getAuthor().setEmail(commentDTO.getAuthor().getEmail());
		updatedComment.getAuthor().setFullName(commentDTO.getAuthor().getFullName());
		comment = commentService.save(updatedComment);
		commentDTO = new CommentDTO(comment);
		
		return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{commentId}")
	public ResponseEntity<Void> delete(@PathVariable("commentId") Integer id)
	{
		Comment comment = commentService.findOne(id);
		if (comment == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		commentService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
