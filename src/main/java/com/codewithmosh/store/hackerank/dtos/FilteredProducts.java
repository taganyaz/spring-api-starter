package com.codewithmosh.store.hackerank.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilteredProducts {
    @JsonProperty("barcode")
    private String barCode;
}
