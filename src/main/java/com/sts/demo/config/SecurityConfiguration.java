package com.sts.demo.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.sts.demo.config.JwtAuthentificationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
	
	
	 private final  JwtAuthentificationFilter jwtAuthFilter;
	private final AuthenticationProvider authentificationProvider;
	 
	 
	 

	public SecurityConfiguration(JwtAuthentificationFilter jwtAuthFilter,
			AuthenticationProvider authentificationProvider) {
		super();
		this.jwtAuthFilter = jwtAuthFilter;
		this.authentificationProvider = authentificationProvider;
	}



 


	@Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
		.securityMatcher("/")
		.csrf()
		.disable()
		  .authorizeHttpRequests(
				    (request) -> {
				    	try {
				    		request.requestMatchers("/api/v1/auth/** ").permitAll()
					    	.anyRequest()
							.authenticated()
							.and()
							.sessionManagement()
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				    	} catch (Exception e) {
		                    throw new RuntimeException(e);
		                  }
				    })
		
		

		.authenticationProvider(authentificationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.httpBasic();
		
		return http.build();	
	}
}
