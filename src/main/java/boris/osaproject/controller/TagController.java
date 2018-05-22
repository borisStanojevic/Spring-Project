package boris.osaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris.osaproject.service.ITagService;

@RestController
@RequestMapping(value = "/posts/{postId}/tags")
public class TagController {

	@Autowired
	private ITagService tagService;
	

}
