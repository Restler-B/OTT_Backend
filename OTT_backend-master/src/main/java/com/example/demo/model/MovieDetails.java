package com.example.demo.model;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "movie_details", schema = "movie_detail")
public class MovieDetails {
	
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
    @JoinTable(name = "movie_genres",
		      joinColumns = { @JoinColumn(name = "movie_id") },
		      inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	private Set<Genre> genres = new HashSet<>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "movie_name")
	private String movie_name;
	
	
	@Column(name = "movie_id")
	private Integer movie_id;
	
	@Column(name = "movie_title")
	private String movie_title;
	
	@Column(name = "movie_description")
	private String movie_description;
	
	@Column(name = "movie_budget")
	private Integer movie_budget;
	
	@Column(name = "m_release_date")
	@Temporal(TemporalType.DATE)
	private Date m_release_date;
	
	@Column(name = "m_revenue")
	private Integer m_revenue;
	
	@Column(name = "m_popularity")
	private Integer m_popularity;
	
	@Column(name = "m_duration")
	private Time m_duration;
	
	@Column(name = "genre_id")
	private Integer genreId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public Integer getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(Integer movie_id) {
		this.movie_id = movie_id;
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

	public Integer getMovie_budget() {
		return movie_budget;
	}

	public void setMovie_budget(Integer movie_budget) {
		this.movie_budget = movie_budget;
	}

	public Date getM_release_date() {
		return m_release_date;
	}

	public void setM_release_date(Date m_release_date) {
		this.m_release_date = m_release_date;
	}

	public Integer getM_revenue() {
		return m_revenue;
	}

	public void setM_revenue(Integer m_revenue) {
		this.m_revenue = m_revenue;
	}

	public Integer getM_popularity() {
		return m_popularity;
	}

	public void setM_popularity(Integer m_popularity) {
		this.m_popularity = m_popularity;
	}

	public Time getM_duration() {
		return m_duration;
	}

	public void setM_duration(Time m_duration) {
		this.m_duration = m_duration;
	}

	public Integer getGenre_id() {
		return genreId;
	}

	public void setGenre_id(Integer genre_id) {
		this.genreId = genre_id;
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
	
}
