package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Trailer;
import com.example.demo.service.TrailerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value ="/api/movies")
@RestController
public class TrailerController {
	@Autowired
		private TrailerService trailerService;
	
	@PostMapping(value = "add_trailer")
	public String addTrailer(@RequestBody Trailer trailer) {
		trailerService.save(trailer);
		return "Created Successfully";
	};
}
