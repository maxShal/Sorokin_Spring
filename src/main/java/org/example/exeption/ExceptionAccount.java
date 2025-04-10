package org.example.exeption;

public class ExceptionAccount extends RuntimeException{
    public ExceptionAccount(String massage)
    {
        super(massage);
    }
}
