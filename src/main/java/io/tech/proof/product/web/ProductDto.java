package io.tech.proof.product.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.tech.proof.product.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductDto(
        Long id,
        @NotBlank String name,
        @Positive Double price,
        @Positive Integer stock
) {
    public static ProductDto newInstance(Product product) {
        return new ProductDto(product.id(), product.name(), product.price(), product.stock());
    }

    public Product toEntity() {
        return new Product(null, this.name(), this.price(), this.stock());
    }

    @JsonIgnore
    public boolean isValid() {
        return this.name() != null && this.price() != null && this.stock() != null;
    }
}
