package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.OtpRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.payload.response.UserInfoResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.service.OtpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/")
@RestController
public class OtpController {
	@Autowired OtpService otpService;
	@Autowired UserRepository userRepo;
	@Autowired
	  AuthenticationManager authenticationManager;
	@Autowired
		PasswordEncoder passwordEncoder;
	
	@Autowired
	 JwtUtils jwtUtils;
	
	@GetMapping(value = "auth/otp")
	public String otpGeneration(@RequestParam (value = "email")String email) throws UnsupportedEncodingException, MessagingException {
		User user = userRepo.findByEmail(email);
		String OTP = otpService.generateOneTimePassword(user);
		return OTP;
	}
	
	 @PostMapping("auth/password_reset")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody OtpRequest otpRequest ) {
	    Boolean email = userRepo.existsByEmail(otpRequest.getEmail());
	    Boolean otp = userRepo.existsByOneTimePassword(otpRequest.getOneTimePassword());
		if(email & otp == true){
			User user = userRepo.findByEmail(otpRequest.getEmail());
			if(!otpService.expired(user)) {
				System.out.println(otpService.expired(user));
				return ResponseEntity.ok(new MessageResponse("Credentials Mismatch!!!"));}
			user.setPassword(passwordEncoder.encode(otpRequest.getPassword()));
			userRepo.save(user);
			otpService.clearOTP(user);
			return ResponseEntity.ok(new MessageResponse("Password changed succesfully!"));
		}else {
			
			return ResponseEntity.ok(new MessageResponse("Credentials Mismatch!"));
		}
	 }
	 
	  
}
