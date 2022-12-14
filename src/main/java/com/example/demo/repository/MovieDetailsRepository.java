package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Genre;
import com.example.demo.model.MovieDetails;

public interface MovieDetailsRepository extends JpaRepository<MovieDetails, Long>{

	Optional<Genre> findBymovieId(int movieId);

	Boolean existsById(long movie_id);

}
