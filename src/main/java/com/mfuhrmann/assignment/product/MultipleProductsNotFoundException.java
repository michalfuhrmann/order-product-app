package com.mfuhrmann.assignment.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@AllArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MultipleProductsNotFoundException extends RuntimeException {
    private final Set<String> ids;
}
