package com.example.bookCommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookCommunity")
public class HomeController {
	
	@GetMapping
	public String home()
	{
		return "home";
	}
	
	@PostMapping("/SignIn")
	public String signIn()
	{
		return "signIn";
	}
	
	@PostMapping("/SignUp")
	public String signUp()
	{
		return "signUp";
	}
	
	@PostMapping("/About")
	public String about()
	{
		return "about";
	}
	
	
}
