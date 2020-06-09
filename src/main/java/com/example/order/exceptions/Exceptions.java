package com.example.order.exceptions;

public class Exceptions {

    public static class CustomerNotFoundException extends NullPointerException{
        public CustomerNotFoundException(final String msg) {
            super(msg);
        }
    }
}
