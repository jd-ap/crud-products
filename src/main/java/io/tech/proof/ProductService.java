package io.tech.proof;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product createFrom(Product product);

    Product updateById(Long id, Product product);

    void deleteById(Long id);

}
