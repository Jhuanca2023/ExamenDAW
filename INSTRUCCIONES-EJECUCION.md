# 🚀 Instrucciones de Ejecución - Microservicios

## 📋 **Orden de Ejecución (IMPORTANTE)**

### 1️⃣ **Paso 1: Iniciar MySQL en Docker**
```bash
.\start-mysql.bat
```
- ✅ MySQL estará disponible en `localhost:3306`
- ✅ Base de datos: `userdb` y `productdb` se crearán automáticamente

### 2️⃣ **Paso 2: Iniciar ConfigServer**
```bash
.\start-config-server.bat
```
- ✅ ConfigServer estará disponible en `http://localhost:8888`
- ✅ Espera a que aparezca "Started ConfigServerApplication"

### 3️⃣ **Paso 3: Iniciar EurekaServer**
```bash
.\start-eureka-server.bat
```
- ✅ EurekaServer estará disponible en `http://localhost:8761`
- ✅ Espera a que aparezca "Started EurekaServerApplication"

### 4️⃣ **Paso 4: Iniciar UserService**
```bash
.\start-user-service.bat
```
- ✅ UserService estará disponible en `http://localhost:8081`
- ✅ Se registrará automáticamente en Eureka

### 5️⃣ **Paso 5: Iniciar ProductService**
```bash
.\start-product-service.bat
```
- ✅ ProductService estará disponible en `http://localhost:8082`
- ✅ Se registrará automáticamente en Eureka

## 🔧 **Comandos Útiles**

### Detener MySQL:
```bash
docker-compose down
```

### Ver logs de MySQL:
```bash
docker logs microservices-mysql
```

### Verificar que MySQL esté funcionando:
```bash
docker ps
```

## 🌐 **URLs de Acceso**

- **MySQL**: `localhost:3306`
- **ConfigServer**: `http://localhost:8888`
- **Eureka Dashboard**: `http://localhost:8761`
- **UserService**: `http://localhost:8081`
- **ProductService**: `http://localhost:8082`

## ⚠️ **Notas Importantes**

1. **Ejecuta en el orden indicado** - Los servicios dependen unos de otros
2. **Espera a que cada servicio termine de iniciar** antes de ejecutar el siguiente
3. **Mantén cada terminal abierta** - Cada servicio debe seguir ejecutándose
4. **Para detener**: Presiona `Ctrl+C` en cada terminal

## 🐛 **Solución de Problemas**

### Si MySQL no inicia:
```bash
docker-compose down
docker system prune -f
.\start-mysql.bat
```

### Si un servicio no se conecta a MySQL:
- Verifica que MySQL esté corriendo: `docker ps`
- Verifica la conexión: `docker logs microservices-mysql`

### Si un servicio no se registra en Eureka:
- Verifica que EurekaServer esté corriendo
- Verifica que ConfigServer esté corriendo
- Revisa los logs del servicio en su terminal
