package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
	List<Genre> findGenreById(int movieId);
}