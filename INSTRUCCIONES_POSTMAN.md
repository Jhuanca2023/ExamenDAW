# 📋 Instrucciones para Postman - Microservicios DAW

## 🚀 **Cómo importar la colección:**

1. **Abrir Postman**
2. **Click en "Import"**
3. **Seleccionar archivo**: `Microservicios_DAW.postman_collection.json`
4. **Click en "Import"**

## 📊 **Estructura de la colección:**

### **1. Eureka Server**
- **Dashboard Eureka**: `http://localhost:8761`
  - Ver microservicios registrados
  - Estado de los servicios

### **2. Config Server**
- **Configuración UserService**: `http://localhost:8888/user-service/default`
- **Configuración ProductService**: `http://localhost:8888/product-service/default`

### **3. UserService - OAuth2 + JWT**
- **Login con Google OAuth2**: `http://localhost:8081/api/auth/login-success`
  - Redirige a Google para autenticación
  - Retorna JWT token
- **Perfil de Usuario**: `http://localhost:8081/api/users/profile`
  - Requiere token JWT en header Authorization
- **Validar Token JWT**: `http://localhost:8081/api/auth/validate`

### **4. ProductService - Resilience4J**
- **Listar Productos**: `http://localhost:8082/api/products`
  - Circuit Breaker (3 fallos)
  - Retry (3 intentos)
  - Timeout (2 segundos)
  - Fallback automático

### **5. Pruebas de Resiliencia**
- **Prueba Circuit Breaker**: Ejecutar 5 veces para ver diferentes comportamientos

## 🔧 **Configuración de variables:**

### **Variable JWT Token:**
1. **Hacer login** con Google OAuth2
2. **Copiar el token** de la respuesta
3. **Ir a Variables** en la colección
4. **Actualizar** `jwt_token` con el token real

## 📋 **Flujo de pruebas recomendado:**

### **Paso 1: Verificar servicios**
1. **Eureka Dashboard** - Ver servicios registrados
2. **Config Server** - Ver configuraciones

### **Paso 2: Probar UserService**
1. **Login con Google** - Obtener JWT token
2. **Actualizar variable** `jwt_token`
3. **Perfil de Usuario** - Usar token JWT
4. **Validar Token** - Verificar token

### **Paso 3: Probar ProductService**
1. **Listar Productos** - Ver respuesta normal
2. **Ejecutar 5 veces** - Ver diferentes fallbacks
3. **Observar** Circuit Breaker, Retry, Timeout

## 🎯 **Respuestas esperadas:**

### **Login exitoso:**
```json
{
  "message": "Login exitoso",
  "user": {
    "id": 1,
    "name": "Tu Nombre",
    "email": "tu.email@gmail.com",
    "imageUrl": "https://..."
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

### **Productos (Fallback por Timeout):**
```json
[
  {
    "id": null,
    "name": "Producto de timeout",
    "description": "Este producto se muestra cuando el servicio tarda demasiado en responder",
    "price": 0,
    "stock": 0
  }
]
```

### **Productos (Fallback por Error):**
```json
[
  {
    "id": null,
    "name": "Producto de ejemplo 1",
    "description": "Este es un producto de ejemplo que se muestra cuando hay un error en el servicio",
    "price": 10.0,
    "stock": 5
  },
  {
    "id": null,
    "name": "Producto de ejemplo 2",
    "description": "Otro producto de ejemplo para el fallback",
    "price": 15.0,
    "stock": 3
  }
]
```

## ⚠️ **Notas importantes:**

1. **Asegúrate** de que todos los servicios estén ejecutándose
2. **Google OAuth2** debe estar configurado correctamente
3. **Ejecuta ProductService varias veces** para ver Resilience4J en acción
4. **Usa el token JWT** para endpoints protegidos

## 🎉 **¡Listo para la presentación!**

Con esta colección puedes demostrar:
- ✅ **Spring Cloud** (Config Server + Eureka)
- ✅ **OAuth2 + JWT** (Google + Seguridad)
- ✅ **Resilience4J** (Circuit Breaker + Retry + Fallback)
- ✅ **Arquitectura de Microservicios** completa
