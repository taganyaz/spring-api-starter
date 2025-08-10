package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name= "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="date_created")
    private LocalDateTime dateCreated = LocalDateTime.now();

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items =  new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(item ->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product) {
        var cartItem = getItem(product.getId());

        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cart(this)
                    .product(product)
                    .quantity((byte) 1)
                    .build();

            items.add(cartItem);
        } else {
            cartItem.setQuantity((byte)(cartItem.getQuantity() +  1));
        }
        return cartItem;
    }

    public CartItem updateItem(Long productId, int quantity) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            cartItem.setQuantity((byte) quantity);
            return  cartItem;
        }
        return null;
    }

    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void removeAll() {
        items.clear();
    }
}
