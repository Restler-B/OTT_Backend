package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MovieDetails;

public interface MovieDetailsRepository extends JpaRepository<MovieDetails, Integer>{
	  List<MovieDetails> findMovieBygenreId(int genreId);
//	  List<MovieDetails> findById(Long movieId);

	

}
