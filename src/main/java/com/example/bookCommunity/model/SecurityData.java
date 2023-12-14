package com.example.bookCommunity.model;

//@Entity
//@Table(name = "Authorization")
public class SecurityData {
	//@Id
	private long user_id;
	//@Column(name = "Login")
	private String login = null;
	//@Column(name = "Password")
	private String password = null;
	public SecurityData(User user , String login,  String password)
	{
		this.user_id = user.getId();
		this.login = login;
		this.password = password;
	}
}
