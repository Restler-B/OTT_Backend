package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.GenreNotFoundException;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.Genre;
import com.example.demo.model.MovieDetails;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.MovieDetailsRepository;

@RestController
public class GenreController {
	
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private MovieDetailsRepository movieDetailsRepository;
	
	@GetMapping("/genres")
	  public ResponseEntity<List<Genre>> getAllgenres() {
	    List<Genre> Genre = new ArrayList<Genre>();
	    genreRepository.findAll().forEach(Genre::add);
	    if (Genre.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(Genre, HttpStatus.OK);
	  }
	
	@GetMapping("/movies/{movieId}/genresById")
	  public ResponseEntity<List<Genre>> getAllGenresByMovieId(@PathVariable(value = "movieId") int movieId) throws MovieNotFoundException {
	    if (!movieDetailsRepository.existsById(movieId)) {
	      throw new MovieNotFoundException("Not found Movie with id = " + movieId);
	    }
	    List<Genre> genres = genreRepository.findGenreBymovieId(movieId);
	    return new ResponseEntity<>(genres, HttpStatus.OK);
	  }
	
	  @GetMapping("/genres/{id}")
	  public ResponseEntity<Genre> getGenresById(@PathVariable(value = "id") int id) throws GenreNotFoundException {
		  Genre genre = genreRepository.findById(id)
	        .orElseThrow(() -> new GenreNotFoundException("Not found genre with id = " + id));
	    return new ResponseEntity<>(genre, HttpStatus.OK);
	  }
	  
	  @GetMapping("/genres/{genreId}/movies")
	  public ResponseEntity<List<MovieDetails>> getAllMoviessByGenreId(@PathVariable(value = "tagId") int genreId) throws GenreNotFoundException {
	    if (!genreRepository.existsById(genreId)) {
	      throw new GenreNotFoundException("Not found Genre  with id = " + genreId);
	    }
	    List<MovieDetails> movies = movieDetailsRepository.findMovieBygenreId(genreId);
	    return new ResponseEntity<>(movies, HttpStatus.OK);
	  }
	  
	  @PostMapping("/movies/{movieId}/genres")
	  public ResponseEntity<Genre> addGenre(@PathVariable(value = "movieId") int movieId, @RequestBody Genre genreRequest) throws MovieNotFoundException, GenreNotFoundException{
		  Genre genre = movieDetailsRepository.findById(movieId).map(movie -> {
			  Integer genreId = genreRequest.getId();
			  
		      // tag is existed
			  if (genreId != 0L) {
				  Genre _genre = null;
				try {
					_genre = genreRepository.findById(genreId)
							  .orElseThrow(()-> new GenreNotFoundException("Not Found genre with Id: " + genreId));
				} catch (GenreNotFoundException e) {
					e.printStackTrace();
				}
				  movie.addGenre(_genre);
				  movieDetailsRepository.save(movie);
				  return _genre;
			  }
		      // add and create new Tag
			  movie.addGenre(genreRequest);
			  return genreRepository.save(genreRequest);
		  }).orElseThrow(() -> new MovieNotFoundException("not Found Movie with Id: " + movieId ));
		    return new ResponseEntity<>(genre, HttpStatus.CREATED);
	  }
	  
	  
	  @PutMapping("/update/genres/{id}")
	  public ResponseEntity<Genre> updateTag(@PathVariable("id") Integer id, @RequestBody Genre genreRequest) throws GenreNotFoundException  {
	    Genre genre = genreRepository.findById(id)
	        .orElseThrow(() -> new GenreNotFoundException("genreId " + id + "not found"));
	    genre.setMovie_genre(genreRequest.getMovie_genre());
	    return new ResponseEntity<>(genreRepository.save(genre), HttpStatus.OK);
	  }
	 
	  @DeleteMapping("/movies/{movieId}/genres/{genreId}")
	  public ResponseEntity<HttpStatus> deleteGenreFromMovie(@PathVariable(value = "movieId") Integer movieId, @PathVariable(value = "genreId") Long genreId) throws MovieNotFoundException {
	    MovieDetails movie = movieDetailsRepository.findById(movieId)
	        .orElseThrow(() -> new MovieNotFoundException("Not found Movie with id = " + movieId));
	    
	    movie.removeGenre(genreId);
	    movieDetailsRepository.save(movie);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  
	  @DeleteMapping("/delete/genres/{id}")
	  public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") Integer id) {
	    genreRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
}
