package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.AddCartItemRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.CartProductNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();

        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addCartItem(UUID cartId, Long productId) {
        var cart = cartRepository.fetchCartByIdWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, int quantity) {
        var cart = cartRepository.fetchCartByIdWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var updatedCartItem = cart.updateItem(productId, quantity);

        if (updatedCartItem == null) {
            throw new CartProductNotFoundException();
        }

        cartRepository.save(cart);

        return cartMapper.toDto(updatedCartItem);
    }

    public void removeCartItem(UUID cartId, Long productId) {
        var cart = cartRepository.fetchCartByIdWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.removeItem(productId);

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.fetchCartByIdWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.removeAll();

        cartRepository.save(cart);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.fetchCartByIdWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public List<CartDto> getCarts() {
        var carts = (List<Cart>) cartRepository.findAll();

        return carts.stream().map(cartMapper::toDto).toList();
    }

}
