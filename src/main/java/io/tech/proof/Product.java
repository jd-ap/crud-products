package io.tech.proof;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTS")
public class Product extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    public Long id;
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public Double price;
    @Column(nullable = false)
    public Integer stock;

}
