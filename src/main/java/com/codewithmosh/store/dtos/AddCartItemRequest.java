package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCartItemRequest {
    @NotNull(message = "Product id is required")
    private Long productId;
}
