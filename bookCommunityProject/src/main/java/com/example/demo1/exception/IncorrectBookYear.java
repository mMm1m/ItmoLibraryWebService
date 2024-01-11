package com.example.demo1;

public class IncorrectBookYear extends  Exception{
    public IncorrectBookYear(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
    public IncorrectBookYear(String msg)
    {
        super(msg);
    }
    public IncorrectBookYear()
    {
        super();
    }
}
