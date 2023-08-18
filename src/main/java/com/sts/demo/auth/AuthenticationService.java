package com.sts.demo.auth;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.demo.config.JwtService;
import com.sts.demo.entity.Role;
import com.sts.demo.entity.User;
import com.sts.demo.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	
	private final UserRepository repository ; 
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(AuthenticationManager authenticationManager,JwtService jwtService,UserRepository repository,PasswordEncoder passwordEncoder) {
		super();
		this.jwtService=jwtService;
		this.repository = repository;
		this.passwordEncoder=passwordEncoder;
		this.authenticationManager=authenticationManager;
	}

	  public AuthenticationResponse register(RegisterRequest request) {
		/*User user = User.builder()
		        .firstname(request.getFirstname())
		        .lastname(request.getLastname())
		        .email(request.getEmail())
		        .password(passwordEncoder.encode(request.getPassword()))
		        .role(Role.USER)
		        .build();
		
		 
		 
		    var savedUser = repository.save(user);
		    
		    */
		    var jwtToken = jwtService.generateToken(null);
		    
		    
		    AuthenticationResponse result = new  AuthenticationResponse();
		    result.setToken(jwtToken);
		    return result;
	}

	 public AuthenticationResponse authenticate(RegisterRequest request) {
		 authenticationManager.authenticate(
		       new UsernamePasswordAuthenticationToken(
			            request.getEmail(),
			            request.getPassword()
			        )
			    );
			    var user = repository.findByEmail(request.getEmail())
			        .orElseThrow();
			    var jwtToken = jwtService.generateToken(user);
			   
			    AuthenticationResponse result = new  AuthenticationResponse();
			    result.setToken(jwtToken);
			    return result;
	}

}
