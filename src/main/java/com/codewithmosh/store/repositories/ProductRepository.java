package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Product;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "category")
    List<Product> findAllByCategoryId(Byte categoryId);

    @Query("select p from Product  p inner join fetch  p.category")
    List<Product> findAllWithCategory();
}