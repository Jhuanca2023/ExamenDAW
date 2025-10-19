package com.cibertec.ProductService.service;

import com.cibertec.ProductService.model.Product;
import com.cibertec.ProductService.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
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
    @TimeLimiter(name = PRODUCT_SERVICE, fallbackMethod = "getAllProductsTimeoutFallback")
    public CompletableFuture<List<Product>> getAllProducts() {
        return CompletableFuture.supplyAsync(() -> {
            try {
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
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Operación interrumpida", e);
            }
        });
    }

    // Método de fallback para Circuit Breaker y Retry
    public List<Product> getAllProductsFallback(Exception e) {
        System.out.println("Fallback ejecutado debido a: " + e.getMessage());
        
        // Crear productos de ejemplo para el fallback
        Product fallbackProduct1 = new Product(
            "Producto de ejemplo 1",
            "Este es un producto de ejemplo que se muestra cuando hay un error en el servicio",
            10.0,
            5
        );
        
        Product fallbackProduct2 = new Product(
            "Producto de ejemplo 2",
            "Otro producto de ejemplo para el fallback",
            15.0,
            3
        );
        
        return List.of(fallbackProduct1, fallbackProduct2);
    }

    // Método de fallback para Timeout
    public CompletableFuture<List<Product>> getAllProductsTimeoutFallback(Exception e) {
        System.out.println("Timeout fallback ejecutado debido a: " + e.getMessage());
        
        Product timeoutProduct = new Product(
            "Producto de timeout",
            "Este producto se muestra cuando el servicio tarda demasiado en responder",
            0.0,
            0
        );
        
        return CompletableFuture.completedFuture(Collections.singletonList(timeoutProduct));
    }
}
