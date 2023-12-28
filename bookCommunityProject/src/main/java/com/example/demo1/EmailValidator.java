package com.example.demo1;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String> {

	@Override
	public boolean test(String t) {
		// email regex
		return true;
	}
	
}
