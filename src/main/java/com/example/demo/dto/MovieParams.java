package com.example.demo.dto;

import java.util.Date;

public class MovieParams {
	private Integer id;
	private String movie_name;
	private Integer movie_id;
	private String movie_title;
	private String movie_description;
	private Integer movie_budget;
	private Date m_release_date;
	private Integer m_revenue;
	private Integer m_popularity;
	private Integer m_duration;
	private Integer genre_id;
	
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
	
	public Integer getM_duration() {
		return m_duration;
	}
	
	public void setM_duration(Integer m_duration) {
		this.m_duration = m_duration;
	}
	
	public Integer getGenre_id() {
		return genre_id;
	}
	
	public void setGenre_id(Integer genre_id) {
		this.genre_id = genre_id;
	}
}
