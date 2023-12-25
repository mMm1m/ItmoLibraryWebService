package com.example.demo1;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
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
	
	//@Autowired
	private final UserRepository repository;
	private final JwtService service;
	private final AuthenticationManager manager;
	
	public AuthenticationResponse register(RegisterRequest request)
	{
		Random random = new Random();
		String id = String.valueOf(random.nextInt(1000000));
		var user = UserEntity.builder()
				.name(request.getName())
				.login(request.getLogin())
				.mail(request.getEmail())
				.password(request.getPassword())
				.build();
		repository.save(user);
		var jwtToken = service.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken).build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request)
	{
		manager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getLogin(),
				request.getPassword()
				));
		var user = repository.findByLogin(request.getLogin()).orElseThrow();
		var jwtToken = service.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
	
}
