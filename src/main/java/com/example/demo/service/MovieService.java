package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.Genre;
import com.example.demo.model.MovieDetails;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.MovieDetailsRepository;

@Service
public class MovieService {

	@Autowired
	private MovieDetailsRepository movieDetailsService;
	@Autowired
	private GenreRepository genreRepository;

	public List<MovieDetails> findAll() {
		return movieDetailsService.findAll();
	}

	public ResponseEntity<?> save(MovieDetails movieDetails) {
		System.out.println("***"+movieDetails.getMovie_id());
		int movie_id = movieDetails.getMovie_id();
		Boolean movie = movieDetailsService.existsById(movie_id);
		System.out.println(movie);
		if (!movie) {
			movieDetailsService.save(movieDetails);
			return new ResponseEntity<>(movieDetails, HttpStatus.CREATED);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie already exists...");
	}
	
	public ResponseEntity<?> updateMovie(MovieDetails movieDetails) throws MovieNotFoundException{
		int movie_id = movieDetails.getMovie_id();
		if (movieDetailsService.existsById(movie_id)) {
			movieDetailsService.save(movieDetails);
			return new ResponseEntity<>(movieDetails, HttpStatus.OK);
		} else {
			throw new MovieNotFoundException("Movie not found with ID" + movie_id);
		}
	}
	
	
	public void saveAll(List<MovieDetails> Moviedetails) {
		movieDetailsService.saveAll(Moviedetails);
	}

	public String deleteMovie(int movie_id) throws MovieNotFoundException {
		Boolean md = movieDetailsService.existsById(movie_id);
		if (md == true) {
			movieDetailsService.deleteById(movie_id);
			return "Deleted Successfully";
		}
		else {
			throw new MovieNotFoundException("Movie not found with ID" + movie_id);
		}
	}
		
	public String deleteAll() {
		movieDetailsService.deleteAll();
		return "Deleted Successfully";
	}
	
	public Optional<MovieDetails> findById(int movie_id) throws MovieNotFoundException{
		Optional<MovieDetails> findMovie = movieDetailsService.findById(movie_id);
		if (findMovie!= null) {
			return findMovie;
		}else {
			throw new MovieNotFoundException("Movie not found with ID" + movie_id);
		}
	}

	public Optional<Genre> findGenre(int movie_id) {
		return genreRepository.findById(movie_id);
	}

	public Genre saveGenre(Genre genre) {
		return genreRepository.save(genre);
	}

	

	
}