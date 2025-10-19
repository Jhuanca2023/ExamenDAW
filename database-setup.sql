-- Script de configuración de base de datos para microservicios
-- Ejecutar este script en MySQL antes de iniciar los servicios

-- Crear bases de datos
CREATE DATABASE IF NOT EXISTS userdb;
CREATE DATABASE IF NOT EXISTS productdb;

-- Usar base de datos userdb
USE userdb;

-- La tabla users se creará automáticamente por JPA/Hibernate
-- con la configuración spring.jpa.hibernate.ddl-auto=update

-- Usar base de datos productdb
USE productdb;

-- La tabla products se creará automáticamente por JPA/Hibernate
-- con la configuración spring.jpa.hibernate.ddl-auto=update

-- Insertar datos de ejemplo para productos (opcional)
-- INSERT INTO products (name, description, price, stock) VALUES
-- ('Laptop Dell', 'Laptop Dell Inspiron 15 3000', 899.99, 10),
-- ('Mouse Logitech', 'Mouse inalámbrico Logitech M705', 29.99, 25),
-- ('Teclado Mecánico', 'Teclado mecánico RGB', 79.99, 15);

-- Verificar que las bases de datos fueron creadas
SHOW DATABASES;
