package com.example.demo1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
	private String name;
	private String login;
	private String email;
	private String password;
}
