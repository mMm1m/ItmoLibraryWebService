package com.example.bookCommunity.model;

public class RequestsForMe extends Request{
	private String text;
	private Response response = null;
	public RequestsForMe(int id , Book book, String text , Response response)
	{
		super(id , book);
		this.text = text;
		this.response = response;
	}
}
