package basati.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import basati.model.Post;

public interface Postrepo extends JpaRepository<Post,Long>{

}
