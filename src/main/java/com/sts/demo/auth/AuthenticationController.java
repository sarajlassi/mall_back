package com.sts.demo.auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.demo.config.JwtService;
import com.sts.demo.repository.UserRepository;

import io.jsonwebtoken.io.IOException;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
 
public class AuthenticationController {
	
	 
	  @Autowired 
	  private 	AuthenticationService service;
	  

	  


	 


	@PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(
	      @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request));
	  }
	  
	  
	  
	  @PostMapping("/authenticate")
	  public ResponseEntity<?> authenticate(
	      @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.authenticate(request));
	  }

	  
	  
	  
	 
}
