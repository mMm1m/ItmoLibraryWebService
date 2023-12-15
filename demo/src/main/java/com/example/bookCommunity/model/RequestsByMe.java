package com.example.bookCommunity.model;

public class RequestsByMe extends Request {
	private Status status = null;
	public RequestsByMe(int id , Book book, Status status)
	{
		super(id , book);
		this.status = status;
	}
}
