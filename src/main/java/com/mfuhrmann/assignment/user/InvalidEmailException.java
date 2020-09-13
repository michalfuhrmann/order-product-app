package com.mfuhrmann.assignment.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException{
    private final String email;
}
