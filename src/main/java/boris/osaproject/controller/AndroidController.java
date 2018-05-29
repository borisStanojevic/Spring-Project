package boris.osaproject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.dto.CommentDTO;
import boris.osaproject.dto.PostDTO;
import boris.osaproject.dto.TagDTO;
import boris.osaproject.dto.UserDTO;
import boris.osaproject.entity.Comment;
import boris.osaproject.entity.Post;
import boris.osaproject.entity.Tag;
import boris.osaproject.entity.User;
import boris.osaproject.entity.Util;

@RestController
@RequestMapping(value="/android-test")
public class AndroidController {
	
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<Set<CommentDTO>> getAllComments(@PathVariable("postId") Integer postId)
	{
		Set<Comment> comments = new HashSet<>();
		for (Post post : Util.posts) {
			if(post.getId() == postId)
			{
				for (Comment comment : post.getComments()) {
					comments.add(comment);
				}
				Set<CommentDTO> commentsDTO = CommentDTO.convert(comments);
				for (CommentDTO commentDTO : commentsDTO) {
					System.out.println(commentDTO.toString());
				}
				return new ResponseEntity<Set<CommentDTO>>(commentsDTO, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Set<CommentDTO>>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/posts/{postId}/comments", consumes="application/json")
	public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") Integer postId, @RequestBody CommentDTO commentDTO)
	{
		for (Post post : Util.posts) {
			if(post.getId() == postId)
			{
				Comment comment = new Comment();
				comment.setId(++Util.commentCounter);
				comment.setContent(commentDTO.getContent());
				for (User user : Util.users) {
					if(user.getUsername().equals(commentDTO.getAuthor().getUsername()))
					{
						comment.setAuthor(user);
						break;
					}
				}
				comment.setPost(post);
				comment.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
				post.getComments().add(comment);
				return new ResponseEntity<CommentDTO>(new CommentDTO(comment), HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<CommentDTO>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<Set<PostDTO>> getAll()
	{
		Set<PostDTO> postsDTO = PostDTO.convert(Util.posts);
		return new ResponseEntity<Set<PostDTO>>(postsDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
	{
		for (Post post : Util.posts) {
			if(post.getId() == id)
			{
				Util.posts.remove(post);
				break;
			}
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDTO> getById(@PathVariable("id") Integer id)
	{
		for (Post post : Util.posts) {
			if(post.getId() == id)
				return new ResponseEntity<PostDTO>(new PostDTO(post), HttpStatus.OK);
		}
		return new ResponseEntity<PostDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/posts", consumes = "application/json")
	public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO)
	{
		Post post = new Post();
		post.setId(++Util.postCounter);
		post.setTitle(postDTO.getTitle());
		for(User user : Util.users)
		{
			if(user.getUsername().equals(postDTO.getAuthor().getUsername()))
			{
				post.setAuthor(user);
				break;
			}
		}
		post.setContent(postDTO.getContent());
		post.setLocationLatitude(postDTO.getLocationLatitude());
		post.setLocationLongitude(postDTO.getLocationLongitude());
		post.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		for (TagDTO tagDTO : postDTO.getTags()) {
			Tag tag = new Tag();
			tag.setName(tagDTO.getName());
			post.getTags().add(tag);
		}
		
		Util.posts.add(post);
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO> getById(@PathVariable String username)
	{
		for (User user: Util.users)
		{
			if (user.getUsername().equals(username))
				return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
	}

}
