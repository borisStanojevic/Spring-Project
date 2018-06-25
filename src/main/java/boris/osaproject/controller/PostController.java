package boris.osaproject.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boris.osaproject.dto.PostDTO;
import boris.osaproject.dto.TagDTO;
import boris.osaproject.entity.Post;
import boris.osaproject.entity.Tag;
import boris.osaproject.entity.User;
import boris.osaproject.service.IPostService;
import boris.osaproject.service.ITagService;
import boris.osaproject.service.IUserService;
import boris.osaproject.util.TokenHelper;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

	@Autowired
	private IPostService postService;
	@Autowired
	private ITagService tagService;
	@Autowired
	private IUserService userService;

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
		User author = userService.findByUsername(postDTO.getAuthor().getUsername());
		post.setAuthor(author);
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		post.setLocationLatitude(postDTO.getLocationLatitude());
		post.setLocationLongitude(postDTO.getLocationLongitude());
		post.setPhoto(postDTO.getPhoto());
		for (TagDTO tagDTO : postDTO.getTags())
		{
			Tag tag = new Tag();
			tag.setName(tagDTO.getName());
			//Ako ne postoji tag dodaj ga u bazu
			if(tagService.findOne(tag.getName()) == null)
				tagService.save(tag);
			//Ako tag vec postoji u bazi samo ga dodaj u kolekciju tagova trenutnog posta koji kreiramo
			post.addTag(tagService.findOne(tag.getName()));
		}
		post = postService.save(post);
		postDTO = new PostDTO(post);
		
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/upload", consumes="multipart/form-data")
	public ResponseEntity<PostDTO> uploadImage(@RequestPart(name = "file") MultipartFile file)
	{
		String photoName = file.getOriginalFilename();
		Integer postId = Integer.parseInt(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")));
		
		Post post = postService.findOne(postId);
		
		File imagesFolder = new File("src/main/resources/static/images/");
		if(!imagesFolder.exists())
			imagesFolder.mkdir();
		imagesFolder.setWritable(true);
		
		try (InputStream in = file.getInputStream();
			 OutputStream out = new FileOutputStream(new File("src/main/resources/static/images/" + photoName)))
		{
			int read = 0;
			byte[] buffer = new byte[1024];
			
			while((read = in.read(buffer)) != -1)
				out.write(buffer, 0 , read);
			
			post.setPhoto(photoName);
			post = postService.save(post);
			
			return new ResponseEntity<PostDTO>(new PostDTO(post), HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<PostDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<PostDTO> update(@RequestBody PostDTO postDTO)
	{
		Post post = postService.findOne(postDTO.getId());
		if(post == null)
			return new ResponseEntity<PostDTO>(HttpStatus.BAD_REQUEST);
		
		post = postService.save(post);
		
		Post updatedPost = new Post();
		updatedPost.setId(post.getId());
		updatedPost.setAuthor(post.getAuthor());
		updatedPost.setTitle(postDTO.getTitle());
		updatedPost.setContent(postDTO.getContent());
		updatedPost.setPhoto(postDTO.getPhoto());
		updatedPost.setDate(postDTO.getDate());
		updatedPost.setLikes(postDTO.getLikes());
		updatedPost.setDislikes(postDTO.getDislikes());
		updatedPost.setLocationLatitude(postDTO.getLocationLatitude());
		updatedPost.setLocationLongitude(postDTO.getLocationLongitude());
		for (TagDTO tagDTO : postDTO.getTags())
		{
			Tag tag = new Tag();
			tag.setName(tagDTO.getName());
			//Ako ne postoji tag dodaj ga u bazu
			if(tagService.findOne(tag.getName()) == null)
				tagService.save(tag);
			//Ako tag vec postoji u bazi samo ga dodaj u kolekciju tagova trenutnog posta koji kreiramo
			updatedPost.addTag(tagService.findOne(tag.getName()));
		}
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
