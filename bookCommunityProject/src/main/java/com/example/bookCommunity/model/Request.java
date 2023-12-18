package com.example.bookCommunity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Request {
	private int request_id;
	private Book book;
	Request(int id , Book book)
	{
		this.request_id = id;
		this.book = book;
	}
}
