package io.tech.proof.product.web;

import io.tech.proof.product.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;

import java.util.Optional;

@Path("/products")
public class ProductsResource implements ProductsApi {

    private final ProductService productService;

    @Inject
    public ProductsResource(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDto[] getProducts() {
        var products = productService.findAll();

        return products.stream()
                .map(ProductDto::newInstance)
                .toArray(ProductDto[]::new);
    }

    @Override
    public ProductDto getProductById(Long id) {
        var product = productService.findById(id);
        return product.map(ProductDto::newInstance).orElseThrow();
    }

    @Override
    public ProductDto createProduct(ProductDto bodyRequest) {
        return Optional.of(bodyRequest)
                .filter(ProductDto::isValid)
                .map(ProductDto::toEntity)
                .map(productService::createFrom)
                .map(ProductDto::newInstance)
                .orElseThrow(() -> new BadRequestException("Las columnas 'name', 'price', 'stock' no permiten valores nulos (NULL)"));
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto bodyRequest) {
        return Optional.of(bodyRequest)
                .map(ProductDto::toEntity)
                .flatMap(it -> productService.updateById(id, it))
                .map(ProductDto::newInstance)
                .orElseThrow();
    }

    @Override
    public void deleteProductById(Long id) {
        productService.deleteById(id);
    }
}
