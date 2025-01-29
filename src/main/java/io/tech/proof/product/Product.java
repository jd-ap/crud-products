package io.tech.proof.product;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer stock;

    public Product() {
    }

    public Product(Long id, String name, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Double price() {
        return price;
    }

    public Integer stock() {
        return stock;
    }

    public void mergeWith(Product product) {
        if(product.name() != null && !product.name().isBlank())
            this.name = product.name();
        if(product.price() != null)
            this.price = product.price();
        if(product.stock() != null)
            this.stock = product.stock();
    }
}
