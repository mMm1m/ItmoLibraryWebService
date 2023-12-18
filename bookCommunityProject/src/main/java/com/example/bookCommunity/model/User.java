package com.example.bookCommunity.model;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name = "Users")
@AllArgsConstructor
public class User {
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long user_id;
	//@Column(name = "MyLibrary")
	private Shelf myLibrarySet = null;
	//@Column(name  = "MyRequests")
	private Set<Request> everyoneToMe = null;
	//@Column(name  = "SentRequests")
	private Set<Request> meToOwner = null;
	//@Column(name = "AllowToRead")
	private List<Book> allowBooks = null;	
	//@Column(name = "Role")
	private Role role = null;
	
	public long getId()
	{
		return this.user_id;
	}
}
