package com.codewithmosh.store.hackerank.dtos;

import com.codewithmosh.store.hackerank.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<Product> data;
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
}
