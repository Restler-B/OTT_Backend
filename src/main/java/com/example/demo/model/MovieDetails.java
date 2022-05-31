package com.example.demo.model;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "movie_details", schema = "movie_details")
public class MovieDetails {
	
	@Column(name = "movie_name")
	@NotBlank
	private String movie_name;
	
	@NotNull
	@Id
	@Column(name = "movie_id")
	private Integer movieId;
	
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

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
    @JoinTable(name = "movie_genres", schema = "movie_details",
		      joinColumns = { @JoinColumn(name = "movie_id") },
		      inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	private Set<Genre> genres = new HashSet<>();
	
	public MovieDetails() {
	}
	
	

	public MovieDetails(@NotBlank String movie_name, @NotNull Integer movieId, @NotBlank String movie_title,
			String movie_description, Long movie_budget, Date m_release_date, Long m_revenue,
			Float m_popularity, @NotBlank String posterPath, @NotBlank String backdropPath,
			@NotBlank String mediaPath, Set<Genre> genres) {
		super();
		this.movie_name = movie_name;
		this.movieId = movieId;
		this.movie_title = movie_title;
		this.movie_description = movie_description;
		this.movie_budget = movie_budget;
		this.m_release_date = m_release_date;
		this.m_revenue = m_revenue;
		this.m_popularity = m_popularity;
		this.posterPath = posterPath;
		this.backdropPath = backdropPath;
		this.mediaPath = mediaPath;
		this.genres = genres;
	}



	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public Integer getMovie_id() {
		return movieId;
	}

	public void setMovie_id(Integer movie_id) {
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
	  
	public Integer getMovieId() {
		return movieId;
	}



	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}



	public Set<Genre> getGenres() {
		return genres;
	}



	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
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

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getMediaPath() {
		return mediaPath;
	}

	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	
}