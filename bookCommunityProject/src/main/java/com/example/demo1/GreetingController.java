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
public class GreetingController {
	@Autowired
	private UserRepository repo;
	
	@GetMapping
	public ResponseEntity<String> home()
	{
		//repo.deleteAll();
		return ResponseEntity.ok("main");
	}
	
	@GetMapping("signIn")
	public ResponseEntity<String> signIn()
	{
        return ResponseEntity.ok("signIn");
	}
	
	@GetMapping("about")
	public ResponseEntity<String> about()
	{
		return ResponseEntity.ok("about");
	}
	
	@GetMapping("signUp")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("signUp");
    }
	
	@Transactional
	@PostMapping("signIn")
    public ResponseEntity<String> auth(@RequestParam String login , @RequestParam String password , Model model) {
		Optional<User> u1 = repo.findByLogin(login);
		Optional<User> u2 = repo.findByPassword(password);
		if(u1 != null && u2 != null)
		{
			if(u1.get().getId() == u2.get().getId()) return ResponseEntity.ok("redirect:/entity");
		}
        return new ResponseEntity<>("signIn", null, 404);
    }
	
	@Transactional
	@PostMapping("signUp")
    public ResponseEntity<String> add(@RequestParam String name ,@RequestParam String login , 
    		@RequestParam String password , @RequestParam String mail , Model model) {
			var message = User.builder()
					.name(name)
					.login(login)
					.mail(mail)
					.password(password).build();
	        repo.save(message);
        Iterable<User> messages = repo.findAll();
        model.addAttribute("messages", messages);
        return ResponseEntity.ok("signIn");
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
	
	/*@GetMapping("/{id}")
	public ResponseEntity<User> userPage(@PathVariable String id , Model model)
	{
		model.addAttribute("id", id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}*/
	
}
