package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.Genre;
//import com.example.demo.exception.MovieNotFound;
import com.example.demo.model.MovieDetails;
import com.example.demo.repository.GenreRepository;
import com.example.demo.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/movies")
@RestController
public class MovieController {
	
	@Autowired
	MovieService movieService;

	@Autowired
	GenreRepository genreRepo;
	
	@GetMapping(value = "/greet")
	public String greet() {
		return "Welcome";
	}

//	Create Movies
	@PostMapping(value = "/create_movie")

	public ResponseEntity<?> createMovie(@RequestBody MovieDetails movieDetails) {
		return movieService.save(movieDetails);
	};
	
	
	
	
	@PutMapping(value = "/update_movie")
	public ResponseEntity<?> updateMovie(@RequestBody MovieDetails movieDetails) throws MovieNotFoundException{
		return movieService.updateMovie(movieDetails);
	}
	
	@GetMapping(value = "/get_allmovies")
	public List<MovieDetails> getMovies() {
		return movieService.findAll();
	};

//	@PostMapping(value = "/batch_entry")
//	public String batchEntry(@RequestBody @Validated List<MovieDetails> Moviedetails) {
//		movieService.saveAll(Moviedetails);
//		return "All Entries Recorded!!";
//	};
	
	@DeleteMapping(value = "/delete_movie/id/{movie_id}")
	public String deleteMovie(@PathVariable (value = "movie_id") int movie_id) throws MovieNotFoundException {
		movieService.deleteMovie(movie_id);
		return "Movie Deleted";
	};
	
	@DeleteMapping(value = "/delete_allmovies")
	public String deleteAll() {
		movieService.deleteAll();
		return "Movies Deleted";
	};
	
	@GetMapping(value = "/get_movie_by_id/{movie_id}")
	public Optional<MovieDetails> getMovie(@PathVariable int movie_id) throws MovieNotFoundException {
		return movieService.findById(movie_id);
	};
	
	
//	
//	@GetMapping(value = "/get_movies/{genre_id}")
//	public Optional<Genre> findGenre(@PathVariable int genre_id) {
//		return movieService.findGenre(genre_id);
//	}
//	
	
}
