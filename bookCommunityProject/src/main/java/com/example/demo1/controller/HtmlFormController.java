package com.example.demo1;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;


@Controller
@RequestMapping("/bookCommunity")
public class HtmlFormController {
	@Autowired
	private UserRepository repo;
	
	@GetMapping
	public String home()
	{
		return "main";
	}
	
	@GetMapping("signIn")
	public String signIn()
	{
        return "signIn";
	}
	
	@GetMapping("about")
	public String about()
	{
		return "about";
	}
	
	@GetMapping("signUp")
    public String main() {
        return "signUp";
    }
	
	@Transactional
	@PostMapping("signIn")
    public String auth(@RequestParam String login , @RequestParam String password , Model model) {
		Optional<User> u1 = repo.findByLogin(login);
		Optional<User> u2 = repo.findByPassword(password);
		if(u1 != null && u2 != null)
		{
			if(u1.get().getId() == u2.get().getId()) return "redirect:/entity";
		}
        return "signIn";
    }
	
	@Transactional
	@PostMapping("signUp")
    public String add(@RequestParam String name ,@RequestParam String login , 
    		@RequestParam String password , @RequestParam String mail , Model model) {
			var message = User.builder()
					.name(name)
					.login(login)
					.mail(mail)
					.password(password).build();
	        repo.save(message);
        Iterable<User> messages = repo.findAll();
        model.addAttribute("messages", messages);
        return "signIn";
    }
	
	// поиск
	@Transactional
    @PostMapping("filter")
    public ResponseEntity<String> filter(@RequestParam Integer filter, Model model) {
    	Optional<User> user = repo.findById(filter);
    	boolean flag = repo.existsById(filter);
    	if(user.isPresent())
        {
    		model.addAttribute("messages", user.get());
        }
    	else model.addAttribute("messages", user);
    	model.addAttribute("exist", flag);
        return ResponseEntity.ok("signUp");
    }
}
