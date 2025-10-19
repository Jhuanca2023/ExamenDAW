package com.cibertec.ProductService.service;

import com.cibertec.ProductService.model.Product;
import com.cibertec.ProductService.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final String PRODUCT_SERVICE = "productService";
    private final Random random = new Random();
    private int attempt = 0;

    @CircuitBreaker(name = PRODUCT_SERVICE, fallbackMethod = "getAllProductsFallback")
    @Retry(name = PRODUCT_SERVICE, fallbackMethod = "getAllProductsFallback")
    public List<Product> getAllProducts() throws InterruptedException {
        // Simular un retraso aleatorio para probar el Circuit Breaker
        if (random.nextBoolean()) {
            // Simular un error aleatorio
            throw new RuntimeException("Error al obtener productos");
        }

        // Simular un retraso mayor al configurado para probar el Timeout
        if (attempt++ % 4 == 0) {
            TimeUnit.SECONDS.sleep(3); // 3 segundos de retraso
        }

        return productRepository.findAll();
    }

    // Método de fallback para Circuit Breaker y Retry
    public List<Product> getAllProductsFallback(Exception e) {
        // Crear un producto de ejemplo para el fallback
        Product fallbackProduct = new Product(
            "Producto de ejemplo",
            "Este es un producto de ejemplo que se muestra cuando hay un error en el servicio",
            0.0,
            0
        );
        
        return Collections.singletonList(fallbackProduct);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
