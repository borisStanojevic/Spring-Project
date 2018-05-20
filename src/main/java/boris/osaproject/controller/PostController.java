package boris.osaproject.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.dto.PostDTO;
import boris.osaproject.entity.Post;
import boris.osaproject.service.IPostService;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

	@Autowired
	private IPostService postService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<String> test(@PathVariable int id)
	{
		return new ResponseEntity<String>(String.valueOf(id), HttpStatus.FOUND);
	}

	@GetMapping(value = "/{id}/comments")
	public void passForward(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("postId", id);
		request.getRequestDispatcher("/comments").forward(request, response);
	}

}
