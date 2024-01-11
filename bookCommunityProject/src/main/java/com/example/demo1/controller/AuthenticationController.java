package com.example.demo1;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;
    
	@PostMapping("/register")
	  public ResponseEntity register(
	      @RequestBody RegisterRequest request
	  ){
		if(service.register(request) != null) return ResponseEntity.ok().build();
		return ResponseEntity.status(413).build();
	  }
	
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token)
	{
		return service.confirmToken(token);
	}
	
	@PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(
	      @RequestBody AuthenticationRequest request
	  ){
	    return ResponseEntity.ok(service.authenticate(request));
	  }
	
	@PostMapping("/refresh-token")
	  public void refreshToken(
	      HttpServletRequest request,
	      HttpServletResponse response
	  ) throws IOException {
	    service.refreshToken(request, response);
	  }
	
	
}
