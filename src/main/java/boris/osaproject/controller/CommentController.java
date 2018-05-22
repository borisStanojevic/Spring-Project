package boris.osaproject.controller;

import java.util.Set;

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
import boris.osaproject.service.ICommentService;

@RestController
@RequestMapping(value = "/posts/{postId}/comments")
public class CommentController {

	@Autowired
	private ICommentService commentService;

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
	public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO commentDTO)
	{
		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		comment.setDate(commentDTO.getDate());
		//Lajkovi i dislajkovi ce inicijalno bit nula posto ih ne postavljam
		comment.getAuthor().setUsername(commentDTO.getAuthor().getUsername());
		comment.getAuthor().setPassword(commentDTO.getAuthor().getPassword());
		comment.getAuthor().setEmail(commentDTO.getAuthor().getEmail());
		comment.getAuthor().setFullName(commentDTO.getAuthor().getFullName());
		
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
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
	{
		Comment comment = commentService.findOne(id);
		if (comment == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		commentService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
