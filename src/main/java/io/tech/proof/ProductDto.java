package io.tech.proof;

public record ProductDto(
        Long id,
        String name,
        Double price,
        Integer stock
) {
    public static ProductDto newInstance(Product product) {
        return new ProductDto(product.id, product.name, product.price, product.stock);
    }

    public Product toEntity() {
        var product = new Product();
        product.name = this.name();
        product.price = this.price();
        product.stock = this.stock();

        return product;
    }
}
