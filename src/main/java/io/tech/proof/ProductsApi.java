package io.tech.proof;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;

public interface ProductsApi {
    //GET /products: Returns the list of all products.
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDto[] getProducts();

    //GET /products/{id}: Returns the product corresponding to the specified ID.
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDto getProductById(@PathParam("id") Long id);

    //POST /products: Allows the creation of a new product.
    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDto createProduct(ProductDto bodyRequest);

    //PUT /products/{id}: Allows updating the details of an existing product.
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDto updateProductById(@PathParam("id") Long id, ProductDto bodyRequest);

    //DELETE /products/{id}: Allows deleting a product by its ID.
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(204)
    void deleteProductById(@PathParam("id") Long id);

}
