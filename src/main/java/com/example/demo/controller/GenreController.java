package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.demo.exception.GenreNotFoundException;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.Genre;
import com.example.demo.model.MovieDetails;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.MovieDetailsRepository;
import com.example.demo.service.GenreService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/movies")
@RestController
public class GenreController {
	
	org.jboss.logging.Logger logger = LoggerFactory.logger(GenreController.class);
	
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private MovieDetailsRepository movieDetailsRepository;
	@Autowired
	GenreService genreService;
	
	
//	Ok
	@PostMapping(value = "/add_genre")
	public String saveGenre(@RequestBody Genre genre) {
		genreService.saveGenre(genre);
		return "Genre Saved Successfully";
	}
	
	
	
//	Get all genres
//	ok
	@GetMapping("/genres")
	  public ResponseEntity<List<Genre>> getAllgenres() {
	    List<Genre> Genre = new ArrayList<Genre>();
	    genreRepository.findAll().forEach(Genre::add);
	    if (Genre.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(Genre, HttpStatus.OK);
	  }
	
//	Get All Genres By Movie Id
	@GetMapping("/{movieId}/genresById")
	  public ResponseEntity<List<Genre>> getAllGenresByMovieId(@PathVariable(value = "movieId") int movieId) throws MovieNotFoundException {
	    if (!movieDetailsRepository.existsById(movieId)) {
	      throw new MovieNotFoundException("Not found Movie with id = " + movieId);
	    }
	    List<Genre> genres = genreRepository.findGenreById(movieId);
	    if (genres.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
	    return new ResponseEntity<>(genres, HttpStatus.OK);
	  }
	

//	USER
//	Get All movies by genre_id
//	Not Satisfied
	  @GetMapping("/genres/{genreId}")
//	  @PreAuthorize("hasRole('USER')")
	  public ResponseEntity<Genre> getGenresById(@PathVariable(value = "genreId") int genreId) throws GenreNotFoundException {
		  Genre genre = genreRepository.findById(genreId)
	        .orElseThrow(() -> new GenreNotFoundException("Not found genre with id = " + genreId));
	    return new ResponseEntity<>(genre, HttpStatus.OK);
	  }
	  
//	  Get All movies By Genre Id
//	  @GetMapping("/genres/{genreId}/movies")
//	  public ResponseEntity<List<MovieDetails>> getAllMoviessByGenreId(@PathVariable(value = "genreId") int genreId) throws GenreNotFoundException {
//	    if (!genreRepository.existsById(genreId)) {
//	      throw new GenreNotFoundException("Not found Genre  with id = " + genreId);
//	    }
//	    List<MovieDetails> movies = movieDetailsRepository.findMovieBygenreId(genreId);
//	    return new ResponseEntity<>(movies, HttpStatus.OK);
//	  }
	  

//	  Ok
//	  Add Genres to existing movies
	  @PostMapping("/{movieId}/genres")
	  public ResponseEntity<Genre> addGenre(@PathVariable(value = "movieId") long movieId, @RequestBody Genre genreRequest) throws MovieNotFoundException, GenreNotFoundException{
		  Genre genre = movieDetailsRepository.findById(movieId).map(movie -> {
			  Integer genreId = genreRequest.getId();
		      // Genre is existed
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
			  logger.info("**********genreRequest Genner Control(125)*********: " + genreRequest);
		      // add and create new Genre
			  movie.addGenre(genreRequest);
			  return genreRepository.save(genreRequest);
		  }).orElseThrow(() -> new MovieNotFoundException("not Found Movie with Id: " + movieId ));
		    return new ResponseEntity<>(genre, HttpStatus.CREATED);
	  }
	  
	  
	  
	  
	  @PostMapping("/mapGenre/{movieId}")
	  public void mapGenre(@PathVariable(value = "movieId") long movieId, @RequestBody List<Integer> genreRequest) throws MovieNotFoundException, GenreNotFoundException {
		  MovieDetails movie = movieDetailsRepository.findById(movieId)
				  .orElseThrow(()-> new GenreNotFoundException("Not Found genre with Id: " + movieId));
		  genreRequest.forEach((Integer id)->{
			  
			  movie.addGenre(genreRepository.getById(id));
		  });
		  movieDetailsRepository.save(movie);
	  }		 
	  
	  
	  
	  
	  
	  
//	  Updating a Genre
//	  Ok
	  @PutMapping("/update/genres/{id}")
	  public ResponseEntity<Genre> updateGenre(@PathVariable("id") Integer id, @RequestBody Genre genreRequest) throws GenreNotFoundException  {
	    Genre genre = genreRepository.findById(id)
	        .orElseThrow(() -> new GenreNotFoundException("genreId " + id + "not found"));
	    genre.setMovie_genre(genreRequest.getMovie_genre());
	    return new ResponseEntity<>(genreRepository.save(genre), HttpStatus.OK);

//	    {
//	        "movie_genre": "Thriller"
//	    }
	    
	  }
	 
	  @DeleteMapping("/{movieId}/genres/{genreId}")
	  public ResponseEntity<HttpStatus> deleteGenreFromMovie(@PathVariable(value = "movieId") long movieId, @PathVariable(value = "genreId") Long genreId) throws MovieNotFoundException {
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
	  
//	  @GetMapping("/get_movie_by_id/{genre_id}")
//	  public List<MovieGenre> findBymovie_id(@PathVariable(value = "genre_id" ) Integer genre_id){
//		  return movieGenreRepository.existsById(genre_id);
//	  }
	  
}
