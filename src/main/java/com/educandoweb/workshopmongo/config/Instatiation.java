package com.educandoweb.workshopmongo.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.educandoweb.workshopmongo.domain.Post;
import com.educandoweb.workshopmongo.domain.User;
import com.educandoweb.workshopmongo.dto.AuthorDTO;
import com.educandoweb.workshopmongo.dto.CommentDTO;
import com.educandoweb.workshopmongo.repository.PostRepository;
import com.educandoweb.workshopmongo.repository.UserRepository;

@Configuration
public class Instatiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	private User maria;
	private User alex;
	private User bob;
	
	private Post post1; 
	private Post post2; 
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private void DefineUsers() {
		userRepository.deleteAll();
		
		maria = new User(null, "Maria Brown", "maria@gmail.com");
		alex = new User(null, "Alex Green", "alex@gmail.com");
		bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));		
	}
	
	private void DefinePosts() throws ParseException {
		postRepository.deleteAll();		
		
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem!", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		postRepository.saveAll(Arrays.asList(post1, post2));

		maria.getPosts().addAll(Arrays.asList(post1, post2));		
		userRepository.save(maria);
	}
	
	private void DefineComments() throws ParseException {
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));
	
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
	}
	
	@Override
	public void run(String... args) throws Exception {
		DefineUsers();
		DefinePosts();
		DefineComments();
	}

}
