package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
}
