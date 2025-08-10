package com.codewithmosh.store.hackerank.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SortedProducts {
    @JsonProperty("barcode")
    private String barCode;
}
