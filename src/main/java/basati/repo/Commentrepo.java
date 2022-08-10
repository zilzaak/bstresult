package basati.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import basati.model.Comment;

public interface Commentrepo  extends JpaRepository<Comment,Long>{

	
	
}
