package basati.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post implements java.io.Serializable{
	   @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	 
	    private String title;
	 
	    @OneToMany(
	        mappedBy = "post",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	    @JsonManagedReference
	    private List<Comment> comments = new ArrayList<>();
	 
	      public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Post() {
			super();
			// TODO Auto-generated constructor stub
		}

		public List<Comment> getComments() {
			return comments;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Post(String title, List<Comment> comments) {
			super();
			this.title = title;
			this.comments = comments;
		}

		public void setComments(List<Comment> comments) {
			this.comments = comments;
		}

		public void addComment(Comment comment) {
	        comments.add(comment);
	        comment.setPost(this);
	    }
	 
	    public void removeComment(Comment comment) {
	        comments.remove(comment);
	        comment.setPost(null);
	    }
}