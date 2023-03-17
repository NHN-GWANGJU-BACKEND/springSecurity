package com.nhnacademy.edu.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GithubLoginFailException extends RuntimeException {
    public GithubLoginFailException(String message) {
        super(message);
    }
}
