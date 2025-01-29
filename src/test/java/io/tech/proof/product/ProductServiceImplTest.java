package io.tech.proof.product;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    transient ProductRepository productRepository;

    @InjectMocks
    transient ProductServiceImpl productService;

    @Test
    void testFindAll_returnsOne() {
        var expected = new Product(0L, "test", 0.0, 0);
        var query = Mockito.mock(PanacheQuery.class);
        Mockito.when(query.stream()).thenReturn(Stream.of(expected));
        Mockito.when(productRepository.findAll()).thenReturn(query);

        var products = productService.findAll();

        assertEquals(1, products.size());
        assertEquals(expected.id(), products.getFirst().id());
    }

    @Test
    void testFindAll_returnsNone() {
        var query = Mockito.mock(PanacheQuery.class);
        Mockito.when(query.stream()).thenReturn(Stream.empty());
        Mockito.when(productRepository.findAll()).thenReturn(query);

        var products = productService.findAll();

        assertEquals(0, products.size());
    }

    @Test
    void testFindById_returnsOne() {
        var expected = new Product(0L, "test", 0.0, 0);
        Mockito.when(productRepository.findByIdOptional(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var result = productService.findById(0L);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void testFindById_returnsNone() {
        Mockito.when(productRepository.findByIdOptional(Mockito.anyLong())).thenReturn(Optional.empty());

        var result = productService.findById(0L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateFrom_returnsOne() {
        Product aProduct = new Product(null, "test", 0.0, 0);

        doNothing().when(productRepository).persist(Mockito.any(Product.class));

        var result = productService.createFrom(aProduct);

        assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).persist(Mockito.any(Product.class));
    }

    @Test
    void testCreateFrom_throwsException() {
        Product aProduct = new Product(null, null, 0.0, 0);

        doThrow(ConstraintViolationException.class)
                .doNothing()
                .when(productRepository).persist(Mockito.any(Product.class));

        var result = assertThrows(ConstraintViolationException.class,
                () -> productService.createFrom(aProduct));

        assertNotNull(result);
        verify(productRepository, times(1)).persist(any(Product.class));
    }

    @Test
    void testUpdateById_returnsOne() {
        Long aId = 0L;
        Product aProduct = new Product(null, "test", null, 1);
        Product storedProduct = new Product(0L, "old test", 10.0, 0);
        Product expected = new Product(0L, "test", 10.0, 1);

        when(productRepository.findByIdOptional(aId)).thenReturn(Optional.of(storedProduct));
        doNothing().when(productRepository).persist(Mockito.any(Product.class));

        var result = productService.updateById(aId, aProduct);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(expected.id(), result.get().id());
        assertEquals(expected.name(), result.get().name());
        assertEquals(expected.price(), result.get().price());
        assertEquals(expected.stock(), result.get().stock());
        verify(productRepository, times(1)).findByIdOptional(anyLong());
        verify(productRepository, times(1)).persist(any(Product.class));
    }

    @Test
    void testUpdateById_throwsNotFound() {
        Long aId = 0L;
        Product aProduct = new Product(null, "test", null, 1);

        when(productRepository.findByIdOptional(aId)).thenReturn(Optional.empty());

        var result = productService.updateById(aId, aProduct);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findByIdOptional(anyLong());
        verify(productRepository, times(0)).persist(any(Product.class));
    }

    @Test
    void testUpdateById_throwsException() {
        Long aId = 0L;
        Product aProduct = new Product(null, "test", null, 1);
        Product storedProduct = new Product(0L, "old test", 10.0, 0);

        when(productRepository.findByIdOptional(aId)).thenReturn(Optional.of(storedProduct));
        doThrow(ConstraintViolationException.class)
                .doNothing()
                .when(productRepository).persist(Mockito.any(Product.class));

        var result = assertThrows(ConstraintViolationException.class,
                () -> productService.updateById(aId, aProduct));

        assertNotNull(result);
        verify(productRepository, times(1)).findByIdOptional(anyLong());
        verify(productRepository, times(1)).persist(any(Product.class));
    }

    @Test
    void testDeleteById_ready() {
        var aId = 0L;
        Product storedProduct = new Product(0L, "test", 0.0, 0);

        when(productRepository.findByIdOptional(aId)).thenReturn(Optional.of(storedProduct));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteById(aId);

        verify(productRepository, times(1)).findByIdOptional(anyLong());
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void testDeleteById_thenNotFound() {
        var aId = 0L;

        when(productRepository.findByIdOptional(aId)).thenReturn(Optional.empty());

        productService.deleteById(aId);

        verify(productRepository, times(1)).findByIdOptional(anyLong());
        verify(productRepository, times(0)).delete(any(Product.class));
    }

    @Test
    void testDeleteById_throwsException() {
        var aId = 0L;
        Product storedProduct = new Product(0L, "test", 0.0, 0);

        when(productRepository.findByIdOptional(aId)).thenReturn(Optional.of(storedProduct));
        doThrow(ConstraintViolationException.class)
                .doNothing()
                .when(productRepository).delete(Mockito.any(Product.class));

        var result = assertThrows(ConstraintViolationException.class,
                () -> productService.deleteById(aId));

        assertNotNull(result);
        verify(productRepository, times(1)).findByIdOptional(anyLong());
        verify(productRepository, times(1)).delete(any(Product.class));
    }
}