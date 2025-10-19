package com.cibertec.ProductService.controller;

import com.cibertec.ProductService.model.Product;
import com.cibertec.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Product>>> getAllProducts() {
        return productService.getAllProducts()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    System.err.println("Error al obtener productos: " + throwable.getMessage());
                    return ResponseEntity.internalServerError().build();
                });
    }
}
