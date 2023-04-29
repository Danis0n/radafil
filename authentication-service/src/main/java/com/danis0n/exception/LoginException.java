package com.danis0n.exception;

public class LoginException extends RuntimeException {

    public LoginException(String username) {
        super(username);
    }

}
