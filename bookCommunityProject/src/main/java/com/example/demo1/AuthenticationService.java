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
	private final TokenRepository tokenRepository;
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
				//.encodedPassword(request.getPassword())
				//.role(request.getRole())
				.role(Role.USER)
				.build();
		var savedUser = repository.save(user);
	    var jwtToken = service.generateToken(user);
	    var refreshToken = service.generateRefreshToken(user);
	    saveUserToken(savedUser, jwtToken);
	    return AuthenticationResponse.builder()
	        .accessToken(jwtToken)
	            .refreshToken(refreshToken)
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
	    var refreshToken = service.generateRefreshToken(user);
	    revokeAllUserTokens(user);
	    saveUserToken(user, jwtToken);
	    return AuthenticationResponse.builder()
	        .accessToken(jwtToken)
	            .refreshToken(refreshToken)
	        .build();
	}
	
	private void saveUserToken(User user, String jwtToken) {
		    var token = Token.builder()
		        .user(user)
		        .token(jwtToken)
		        .tokenType(TokenType.BEARER)
		        .expired(false)
		        .revoked(false)
		        .build();
		    tokenRepository.save(token);
		 }

		  private void revokeAllUserTokens(User user) {
		    var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
		    if (!validUserTokens.isEmpty()) return;
		    validUserTokens.forEach(token ->
		    {token.setExpired(true);
		    token.setRevoked(true);
		    });
		    tokenRepository.saveAll(validUserTokens);
		  }
		  
		  public void refreshToken(
		          HttpServletRequest request,
		          HttpServletResponse response
		  ) throws IOException {
		    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		    final String refreshToken;
		    final String userEmail;
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      return;
		    }
		    refreshToken = authHeader.substring(7);
		    userEmail = service.extractUserLogin(refreshToken);
		    if (userEmail != null) {
		      var user = this.repository.findByLogin(userEmail)
		              .orElseThrow();
		      if (service.isTokenValid(refreshToken, user)) {
		        var accessToken = service.generateToken(user);
		        revokeAllUserTokens(user);
		        saveUserToken(user, accessToken);
		        var authResponse = AuthenticationResponse.builder()
		                .accessToken(accessToken)
		                .refreshToken(refreshToken)
		                .build();
		        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
		      }
		    }
		  }
	
}
