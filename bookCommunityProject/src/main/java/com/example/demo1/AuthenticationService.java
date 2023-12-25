package com.example.demo1;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo1.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@Builder
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repository;
	private final JwtService service;
	private final AuthenticationManager manager;
	private final PasswordEncoder encoder;
	
	public AuthenticationResponse register(RegisterRequest request)
	{
		var user = User.builder()
				.name(request.getName())
				.login(request.getLogin())
				.mail(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
	    var jwtToken = service.generateToken(user);
	    return AuthenticationResponse.builder()
	        .token(jwtToken)
	        .build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request)
	{
		manager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getLogin(),
				request.getPassword()
				));
		var user = repository.findByLogin(request.getLogin())
		        .orElseThrow();
		    var jwtToken = service.generateToken(user);
		    return AuthenticationResponse.builder()
		        .token(jwtToken)
		        .build();
	}
	
	
}
