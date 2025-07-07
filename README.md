# 🛒 Shopping Cart API

Esta es una API RESTful construida con **Spring Boot 2.7**, que permite gestionar carritos de compras, productos y descuentos aplicables por categoría. Incluye documentación interactiva mediante Swagger y soporte para ejecución en contenedores Docker.

---

## 📌 Funcionalidad principal

La API permite:

- Crear carritos de compra para un usuario
- Agregar y eliminar productos del carrito
- Obtener carritos por ID o por usuario
- Procesar órdenes asincrónicamente (simulado) aplicando descuentos automáticos según la categoría de los productos

---

## 🚀 Levantar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/shopping-cart-api.git
cd shopping-cart-api
```

### 2. Levantar usando Docker
```bash
docker compose up --build
```

## 📄 Acceso a la documentación Swagger

Una vez que la aplicación está corriendo, se puede acceder a la documentación interactiva de los endpoints mediante Swagger UI en la siguiente URL:

➡️ [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Desde ahí se podrá:

- Explorar todos los endpoints disponibles
- Probar llamadas directamente desde el navegador
- Ver los modelos utilizados (carrito, producto, etc.)
- Ver respuestas esperadas y códigos HTTP

