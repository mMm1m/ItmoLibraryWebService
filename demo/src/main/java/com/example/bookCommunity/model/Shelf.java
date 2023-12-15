package com.example.bookCommunity.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name = "UserShelf")
@AllArgsConstructor
public class Shelf {
	//@Id
	private long user_id;
	private Set<Book> book;
	
	public Shelf(@NonNull User user, Set<Book> book)
	{
		this.user_id = user.getId();
		this.book = book;
	}
}
