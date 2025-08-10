package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CartProductDto {
    public Long id;
    public String name;
    public BigDecimal price;
}
