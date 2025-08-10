package com.codewithmosh.store.hackerank.services;

import com.codewithmosh.store.hackerank.dtos.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.codewithmosh.store.hackerank.models.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private static final String API_URL = "https://jsonmock.hackerrank.com/api/inventory";

    private final RestTemplate restTemplate = new RestTemplate();
    private List<Product> productsCache = null;

    public List<Product> getAllProducts() {
        if (productsCache != null) {
            return productsCache;
        }

        List<Product> allProducts = new ArrayList<>();
        int page = 1;
        ApiResponse response = null;

        do
        {
            String url = API_URL + "?page=" + page;
            response = restTemplate.getForObject(url, ApiResponse.class);
            if (response != null && response.getData() != null) {
                allProducts.addAll(response.getData());
            }
            page++;
        } while (response != null && page <= response.getTotal_pages());

        productsCache = allProducts;

        return allProducts;
    }
}
