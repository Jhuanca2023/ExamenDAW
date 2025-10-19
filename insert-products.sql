-- Script para insertar productos de ejemplo en la base de datos
USE productdb;

-- Insertar productos de ejemplo
INSERT INTO products (name, description, price, stock) VALUES
('Laptop Dell XPS 13', 'Laptop ultradelgada con pantalla 13 pulgadas, Intel i7, 16GB RAM, 512GB SSD', 1299.99, 10),
('iPhone 15 Pro', 'Smartphone Apple con cámara de 48MP, chip A17 Pro, 256GB almacenamiento', 999.99, 25),
('Samsung Galaxy S24', 'Smartphone Android con pantalla AMOLED 6.2", 128GB, 8GB RAM', 799.99, 30),
('MacBook Air M2', 'Laptop Apple con chip M2, pantalla 13.6", 8GB RAM, 256GB SSD', 1199.99, 15),
('iPad Pro 12.9"', 'Tablet Apple con pantalla Liquid Retina XDR, chip M2, 128GB', 1099.99, 20),
('Sony WH-1000XM5', 'Audífonos inalámbricos con cancelación de ruido, 30h batería', 399.99, 50),
('Nintendo Switch OLED', 'Consola de videojuegos portátil con pantalla OLED 7"', 349.99, 40),
('PlayStation 5', 'Consola de videojuegos de nueva generación, 825GB SSD', 499.99, 12),
('Xbox Series X', 'Consola de videojuegos Microsoft, 1TB SSD, 4K', 499.99, 8),
('Apple Watch Series 9', 'Smartwatch con GPS, pantalla Always-On, 45mm', 399.99, 35);

-- Verificar que se insertaron correctamente
SELECT COUNT(*) as total_products FROM products;
SELECT * FROM products LIMIT 5;
