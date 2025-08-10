package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.*;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.CartProductNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;


    @GetMapping
    public List<CartDto> getCarts() {
        return cartService.getCarts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> GetCart(@PathVariable UUID id) {
        var cartDto = cartService.getCart(id);

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cartDto = cartService.createCart();

        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addCartItem(
            @PathVariable UUID id,
            @Valid @RequestBody() AddCartItemRequest request) {

        var cartItemDto = cartService.addCartItem(id, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PutMapping("{id}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable UUID id,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        var updatedCartItem = cartService.updateCartItem(id, productId, request.getQuantity());

        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{id}/items/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable UUID id,
            @PathVariable Long productId
    ) {
        cartService.removeCartItem(id, productId);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{id}/items")
    public ResponseEntity<?> clearCartItems(
            @PathVariable UUID id
    ) {
        cartService.clearCart(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    private ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found ")
        );
    }

    @ExceptionHandler({ProductNotFoundException.class})
    private ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Product not found")
        );
    }

    @ExceptionHandler({CartProductNotFoundException.class})
    private ResponseEntity<Map<String, String>> handleCartProductNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Product not found in the cart")
        );
    }
}
