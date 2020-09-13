package com.mfuhrmann.assignment.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetAllProductsResponse {
    private final List<GetAllProductsView> products;
}
