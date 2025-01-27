package io.tech.proof;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> findAll() {
        return Product
                .<Product>findAll()
                .stream().toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Product.findByIdOptional(id);
    }

    @Override
    @Transactional
    public Product createFrom(Product product) {
        product.persist();
        return product;
    }

    @Override
    @Transactional
    public Product updateById(Long id, Product product) {
        return Product.<Product>findByIdOptional(id)
                .map(it -> {
                    if(product.name != null && !product.name.isBlank())
                        it.name = product.name;
                    if(product.price != null)
                           it.price = product.price;
                    if(product.stock != null)
                            it.stock = product.stock;

                    it.persist();
                    return it;
                }).orElseThrow(() -> new RuntimeException("No existe")); // ToDo: mejorar manejo de excepciones
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product.<Product>findByIdOptional(id)
                .ifPresent(Product::delete);
    }

}
