package io.tech.proof.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository
                .findAll()
                .stream().toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findByIdOptional(id);
    }

    @Override
    @Transactional
    public Product createFrom(Product product) {
        productRepository.persist(product);
        return product;
    }

    @Override
    @Transactional
    public Optional<Product> updateById(Long id, Product product) {
        return productRepository.findByIdOptional(id)
                .map(it -> {
                    it.mergeWith(product);

                    productRepository.persist(it);
                    return it;
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.findByIdOptional(id)
                .ifPresent(productRepository::delete);
    }

}
