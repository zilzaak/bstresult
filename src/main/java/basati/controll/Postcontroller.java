package basati.controll;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import basati.model.Comment;
import basati.model.Post;
import basati.repo.Commentrepo;
import basati.repo.Postrepo;

@Controller
@RequestMapping("/post")
public class Postcontroller {

	@Autowired
	private Postrepo prr;
	
	@Autowired
private Commentrepo cmr;
	
	
	@RequestMapping("/add")	
	public String addpost() {
		
		Post k=new Post();
		k.setTitle("tarek hossain");
		Comment pc=new Comment();
		Comment pc1=new Comment();
		Comment pc2=new Comment();
		pc.setReview("tarek hossain comment 1"); pc.setPost(k);
		pc1.setReview("tarek hossain comment2");pc1.setPost(k);
		pc2.setReview("tarek hossain comment3");pc2.setPost(k);
		List<Comment> cmnts=new ArrayList<Comment>();cmnts.add(pc1);
		cmnts.add(pc);cmnts.add(pc2);
		k.setComments(cmnts);
	prr.save(k);
	return "post";
	}
	
		
@GetMapping("/allpost")	
	public ResponseEntity<List<Post>> allpost() {
	List<Post> posts=prr.findAll();
	return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);

	}


	
	
}
