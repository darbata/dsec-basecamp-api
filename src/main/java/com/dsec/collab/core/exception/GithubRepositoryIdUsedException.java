package com.dsec.collab.core.exception;

public class GithubRepositoryIdUsedException extends RuntimeException {
    public GithubRepositoryIdUsedException (String message) {
        super(message);
    }
}