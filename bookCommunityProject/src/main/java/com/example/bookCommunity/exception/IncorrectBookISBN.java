package com.example.bookCommunity.exception;

public class IncorrectBookISBN extends Exception {
    public IncorrectBookISBN(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
    public IncorrectBookISBN(String msg)
    {
        super(msg);
    }
    public IncorrectBookISBN()
    {
        super();
    }
}