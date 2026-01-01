package com.dsec.collab.core.exception;

public class GithubAuthenticationException extends RuntimeException {
    public GithubAuthenticationException(String message) {
        super(message);
    }
}