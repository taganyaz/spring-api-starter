package com.codewithmosh.store.hackerank.models;

import lombok.Data;

@Data
public class Product {
    private  String barcode;
    private String item;
    private String category;
    private double price;
    private  int discount;
    private int available;
}
