package com.mfuhrmann.assignment.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@AllArgsConstructor
@ToString
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateSKUException extends RuntimeException {
    private final String sku;
}
