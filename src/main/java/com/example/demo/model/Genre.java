package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "genre", schema = "movie_details")
public class Genre {

	@Id
	@Column(name= "genre_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "movie_genre")
	private String movie_genre;

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      },
		      mappedBy = "genres")
	@JsonIgnore
	private Set<MovieDetails> movieDetails = new HashSet<>();
	
	public Genre() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMovie_genre() {
		return movie_genre;
	}

	public void setMovie_genre(String movie_genre) {
		this.movie_genre = movie_genre;
	}

	public Set<MovieDetails> getMovies() {
		return movieDetails;
	}
	
	public void setMovies(Set<MovieDetails> movieDetails) {
	    this.movieDetails = movieDetails;
	  }
}