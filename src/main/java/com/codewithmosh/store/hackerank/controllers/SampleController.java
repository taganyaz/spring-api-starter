package com.codewithmosh.store.hackerank.controllers;

import com.codewithmosh.store.hackerank.dtos.FilteredProducts;
import com.codewithmosh.store.hackerank.dtos.SortedProducts;
import com.codewithmosh.store.hackerank.models.Product;
import com.codewithmosh.store.hackerank.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class SampleController {
    private final ProductService productService;

    public SampleController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/filter/price/{initial_price}/{final_price}")
    public ResponseEntity<List<FilteredProducts>> getFilteredProductsByPrice(
            @PathVariable("initial_price") double initialPrice,
            @PathVariable("final_price") double finalPrice) {

        List<Product> products = productService.getAllProducts();

        List<FilteredProducts> filteredProducts = products.stream()
                .filter(p -> p.getPrice() >= initialPrice && p.getPrice() <= finalPrice)
                .map(p -> new FilteredProducts(p.getBarcode()))
                .toList();
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/sort/price")
    public ResponseEntity<List<SortedProducts>> sortedProductsByPrice() {
        List<Product> products = productService.getAllProducts();

        var sortedProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .map(p -> new SortedProducts(p.getBarcode()))
                .toList();

        return ResponseEntity.ok(sortedProducts);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Error encountered: " + e.getMessage());
    }
}
