package boris.osaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.dto.PostDTO;
import boris.osaproject.entity.Post;
import boris.osaproject.service.IPostService;
import boris.osaproject.service.ITagService;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

	@Autowired
	private IPostService postService;

	@GetMapping
	public ResponseEntity<Set<PostDTO>> getAll()
	{
		Set<Post> posts = postService.findAll();
		Set<PostDTO> postsDTO = PostDTO.convert(posts);
		return new ResponseEntity<Set<PostDTO>>(postsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PostDTO> getById(@PathVariable("id") Integer id)
	{
		Post post = postService.findOne(id);
		if (post != null)
		{
			PostDTO postDTO = new PostDTO(post);
			return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
		}
		return new ResponseEntity<PostDTO>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO)
	{
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		//post.setDate(postDTO.getDate());
		post.setLocation(postDTO.getLocation());
		post.setPhoto(postDTO.getPhoto());
		//Tagovi !
		
		post = postService.save(post);
		postDTO = new PostDTO(post);
		
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<PostDTO> update(@RequestBody PostDTO postDTO)
	{
		Post post = postService.findOne(postDTO.getId());
		if(post == null)
			return new ResponseEntity<PostDTO>(HttpStatus.BAD_REQUEST);
		Post updatedPost = new Post();
		updatedPost.setId(postDTO.getId());
		updatedPost.setTitle(postDTO.getTitle());
		updatedPost.setContent(postDTO.getContent());
		updatedPost.setPhoto(postDTO.getPhoto());
		
		post = postService.save(updatedPost);
		postDTO = new PostDTO(post);
		
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);	
	}
	
	@PatchMapping(consumes = "application/json")
	public ResponseEntity<PostDTO> updateLikesDislikes(@RequestBody PostDTO postDTO)
	{
		Post post = postService.findOne(postDTO.getId());
		if(post == null)
			return new ResponseEntity<PostDTO>(HttpStatus.BAD_REQUEST);
		if(post.getLikes() != postDTO.getLikes())
			post.setLikes(postDTO.getLikes());
		if(post.getDislikes() != postDTO.getDislikes())
			post.setDislikes(postDTO.getDislikes());
		postService.save(post);
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
	{
		Post post = postService.findOne(id);
		if(post == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		postService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
