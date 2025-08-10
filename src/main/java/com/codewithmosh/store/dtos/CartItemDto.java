package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CartItemDto {
    public CartProductDto product;
    public Byte quantity;
    public BigDecimal totalPrice;
}
