package boris.osaproject.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Util {
	public static Integer postCounter = 0;
	public static Integer commentCounter = 0;
	public static Set<Post> posts = new HashSet<>();
	public static Set<User> users = new HashSet<>();
	
	
	static
	{
		User u = new User();
		u.setUsername("mitar123");
		u.setPassword("123");
		u.setEmail("mitar123@grand.org");
		u.setFullName("Mitar Miric");
		
		User u2 = new User();
		u2.setUsername("saban123");
		u2.setPassword("123");
		u2.setEmail("saban123@grand.org");
		u2.setFullName("Sahban Sahulic");
		
		users.add(u);
		users.add(u2);
		
		for(int i = 1 ; i < 3 ; i++)
		{
			Post p = new Post();
			p.setId(++postCounter);
			p.setContent("Objava broj " + i);
			p.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
			p.setTitle("Naslov objave broj " + i);
			p.setAuthor(u);
			Comment c = new Comment();
			c.setId(++commentCounter);
			c.setContent("Komentar " + i);
			c.setAuthor(u2);
			c.setPost(p);
			c.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
			p.getComments().add(c);
			Tag t = new Tag();
			t.setName("Tag " + i);
			p.getTags().add(t);
			posts.add(p);
			
		}
		
		for(int i = 4 ; i < 6 ; i++)
		{
			Post p = new Post();
			p.setId(++postCounter);
			p.setContent("Objava broj " + i);
			p.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));;
			p.setTitle("Naslov objave broj " + i);
			p.setAuthor(u2);
			Comment c = new Comment();
			c.setId(++commentCounter);
			c.setContent("Komentar " + i);
			c.setAuthor(u);
			c.setPost(p);
			c.setDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
			p.getComments().add(c);
			Tag t = new Tag();
			t.setName("Tag " + i);
			p.getTags().add(t);
			posts.add(p);
		}
		
	}
}
