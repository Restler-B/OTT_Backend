package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "movie_details", schema = "movie_details")
public class MovieDetails {
	
	@Id
	@NotNull
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private Long movieId;
	
	@Column(name = "movie_name")
	@NotBlank
	private String movie_name;
	
	@Column(name = "movie_title")
	@NotBlank
	private String movie_title;
	
	@Column(name = "movie_description")
	private String movie_description;
	
	@Column(name = "movie_budget")
	private Long movie_budget;
	
	@Column(name = "m_release_date")
	@Temporal(TemporalType.DATE)
	private Date m_release_date;
	
	@Column(name = "m_revenue")
	private Long m_revenue;
	
	@Column(name = "m_popularity")
	private Float m_popularity;
	
	@Column(name = "poster_path")
	@NotBlank
	private String posterPath;
	
	@Column(name = "backdrop_path")
	@NotBlank
	private String backdropPath;
	
	@Column(name = "media_path")
	@NotBlank
	private String mediaPath;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })

	@JoinTable(name = "movie_genres",schema = "movie_details",
		      joinColumns = { @JoinColumn(name = "movie_id") },
		      inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	private Set<Genre> genres = new HashSet<>();
	
	

//
//	public Set<Trailer> getTrailer() {
//		return trailer;
//	}
//
//	public void setTrailer(Set<Trailer> trailer) {
//		this.trailer = trailer;
//	}
//	
	public String getMovie_name() {
		return movie_name;
	}
	
	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public Long getMovie_id() {
		return movieId;
	}

	public void setMovie_id(Long movie_id) {
		this.movieId = movie_id;
	}

	public String getMovie_title() {
		return movie_title;
	}

	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}

	public String getMovie_description() {
		return movie_description;
	}

	public void setMovie_description(String movie_description) {
		this.movie_description = movie_description;
	}

	public Long getMovie_budget() {
		return movie_budget;
	}

	public void setMovie_budget(Long movie_budget) {
		this.movie_budget = movie_budget;
	}

	public Date getM_release_date() {
		return m_release_date;
	}

	public void setM_release_date(Date m_release_date) {
		this.m_release_date = m_release_date;
	}

	public Long getM_revenue() {
		return m_revenue;
	}

	public void setM_revenue(Long m_revenue) {
		this.m_revenue = m_revenue;
	}

	public Float getM_popularity() {
		return m_popularity;
	}

	public void setM_popularity(Float m_popularity) {
		this.m_popularity = m_popularity;
	}

	public void addGenre(Genre _genre) {
	    this.genres.add(_genre);
	    _genre.getMovies().add(this);
	}
	  
	public void removeGenre(long genreId) {
	    Genre genre = this.genres.stream().filter(t -> t.getId() == genreId).findFirst().orElse(null);
	    if (genre != null) {
	      this.genres.remove(genre);
	      genre.getMovies().remove(this);
	    }
	  }

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getBackdrop_path() {
		return backdropPath;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdropPath = backdrop_path;
	}

	public String getMedia_path() {
		return mediaPath;
	}

	public void setMedia_path(String media_path) {
		this.mediaPath = media_path;
	}

//	public Set<Genre> getGenres() {
//		return genres;
//	}
//
//	public void setGenres(Set<Genre> genres) {
//		this.genres = genres;
//	}
	

	public Set<Genre> getGenres() {
		return genres;
	}
//
//	public void setGenres(Set<Genre> genres) {
//		this.genres = genres;
//	}
	
	
}
