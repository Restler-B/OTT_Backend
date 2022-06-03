package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Trailer;
import com.example.demo.repository.TrailerRepository;
@Service
public class TrailerService {

		@Autowired
			private TrailerRepository trailerRepository;
		
	public void save(Trailer trailer) {
		trailerRepository.save(trailer);
	}

}
