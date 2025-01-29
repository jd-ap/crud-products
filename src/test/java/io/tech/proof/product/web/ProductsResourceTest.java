package io.tech.proof.product.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@QuarkusTest
class ProductsResourceTest {

    private final String pathCollection = "/products";

    @Test
    void testGetProducts_returnsAll() {
        given()
                .when().get(pathCollection)
                .then()
                .statusCode(200)
                .body(not("[]"));
    }

    @Test
    void testGetProductById_whenResourceExists() {
        var expected = "{\"id\":1,\"name\":\"laptop\",\"price\":100000.0,\"stock\":20}";

        given()
                .when().get(pathCollection + "/" + 1)
                .then()
                .statusCode(200)
                .body(is(expected));
    }

    @Test
    void testGetProductById_whenResourceNotExists() {
        given()
                .when().get(pathCollection + "/" + 0)
                .then()
                .statusCode(404)
                .body(is(""));
    }

    @Test
    void testCreateProduct_returnsNewResource() {
        var body = "{\"name\":\"product 12\",\"price\":1.0,\"stock\":2}";
        var expected = "{\"id\":12,\"name\":\"product 12\",\"price\":1.0,\"stock\":2}";

        given().header("Content-type", "application/json")
                .and()
                .body(body)
                .when().post(pathCollection)
                .then()
                .statusCode(201)
                .body(is(expected));
    }

    @Test
    void testCreateProduct_whenBadRequest() {
        var body = "{\"name\":\"product fail\"}";
        var expected = "{\"code\":\"BAD_REQUEST\",\"message\":\"Las columnas 'name', 'price', 'stock' no permiten valores nulos (NULL)\",\"details\":null}";

        given().header("Content-type", "application/json")
                .and()
                .body(body)
                .when().post(pathCollection)
                .then()
                .statusCode(400)
                .body(is(expected));
    }

    @Test
    void testUpdateProductById_returnsAlteredResource() {
        var body = "{\"name\":\"product 0\",\"price\":1.0,\"stock\":2}";
        var expected = "{\"id\":2,\"name\":\"product 0\",\"price\":1.0,\"stock\":2}";

        given().header("Content-type", "application/json")
                .and()
                .body(body)
                .when().put(pathCollection + "/2")
                .then()
                .statusCode(200)
                .body(is(expected));
    }

    @Test
    void testUpdateProductById_whenResourceNotExists() {
        var body = "{\"name\":\"product 0\",\"price\":1.0,\"stock\":2}";

        given().header("Content-type", "application/json")
                .and()
                .body(body)
                .when().put(pathCollection + "/0")
                .then()
                .statusCode(404);
    }

    @Test
    void testDeleteProductById_whenResourceExists() {
        given()
                .when().delete(pathCollection + "/10")
                .then()
                .statusCode(204);
        given()
                .when().get(pathCollection + "/10")
                .then()
                .statusCode(404);
    }

    @Test
    void testDeleteProductById_whenResourceNotExists() {
        given()
                .when().delete(pathCollection + "/0")
                .then()
                .statusCode(204);
        given()
                .when().get(pathCollection + "/0")
                .then()
                .statusCode(404);
    }
}