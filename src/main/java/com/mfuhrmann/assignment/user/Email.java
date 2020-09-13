package com.mfuhrmann.assignment.user;

import lombok.Data;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.annotation.PersistenceConstructor;

@Data
public class Email {
    private String name;

    @PersistenceConstructor
    public Email(String name) {
        this.name = name;
        boolean valid = EmailValidator.getInstance().isValid(name);
        if (!valid) {
            throw new InvalidEmailException(name);
        }
    }
}
