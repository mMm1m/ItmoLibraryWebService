package com.example.demo1;


public class IncorrectBookID extends Exception{
    public IncorrectBookID()
    {
        super();
    }
    public IncorrectBookID(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
    public IncorrectBookID(String msg)
    {
        super(msg);
    }
}
