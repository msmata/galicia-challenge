# 📊 Informe de Pruebas de Carga - API Carrito de Compras

## ✅ Herramienta utilizada

**k6** (https://k6.io)  
Simulador de carga open-source, basado en JavaScript. Ideal para automatizar y versionar pruebas de rendimiento.

---

## 🧪 Escenario de prueba

- **Objetivo:** Evaluar el rendimiento del endpoint autenticado `/carts/user/` bajo carga concurrente.
- **Usuarios virtuales:** 20 (`VUs`)
- **Duración:** 50 segundos de carga sostenida (con ramp-up y ramp-down)
- **Iteraciones:** Cada VU ejecuta una solicitud por segundo
- **Acciones por iteración:**
  1. Autenticación vía JWT (`/auth/login`)
  2. Consulta de carritos del usuario (`/carts/user/`)
- **Total de solicitudes:** 1564 (2 por iteración x 782 iteraciones)

---

## ⚙️ Configuración del test

```js
export let options = {
  stages: [
    { duration: '10s', target: 20 },  // subida
    { duration: '30s', target: 20 },  // carga sostenida
    { duration: '10s', target: 0 },   // bajada
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'],
  },
};
```

---

## 📈 Resultados generales

| Métrica                       | Valor              | Interpretación                              |
|-------------------------------|--------------------|---------------------------------------------|
| ✔ Solicitudes exitosas        | 100% (1564/1564)   | Todas las respuestas fueron válidas        |
| ✗ Errores HTTP                | 0%                 | Ninguna request falló                       |
| ✔ Status 200                  | 100%               | Todos los endpoints respondieron OK         |
| ✔ Umbral p(95)<500ms          | p(95)=61.85ms      | 95% de las respuestas en menos de 62ms      |
| 🔁 Iteraciones totales        | 782                | 15.6 iteraciones/segundo promedio           |
| 🚀 Throughput                 | 31.15 requests/seg | Tasa de solicitudes sostenida               |
| ⚡ Latencia promedio           | 20.19 ms           | Excelente tiempo de respuesta               |

---

## 📊 Latencias detalladas

| Percentil         | Tiempo de respuesta |
|-------------------|---------------------|
| Promedio (`avg`)  | 20.19 ms            |
| Mediana (`med`)   | 14.7 ms             |
| p90               | 43.38 ms            |
| **p95**           | **61.85 ms**        |
| Máximo (`max`)    | 273.44 ms           |

---

## 🧠 Observaciones

- El test incluye login en cada iteración, lo cual representa un escenario realista pero más exigente.
- La API mantuvo una latencia extremadamente baja y estable, sin errores de concurrencia, timeout o disponibilidad.
- El diseño actual del backend es robusto y está listo para escalar bajo condiciones de producción con usuarios concurrentes moderados.

---

## 📌 Recomendación

> “La API respondió de forma consistente y eficiente bajo carga concurrente. Todas las solicitudes fueron procesadas exitosamente, con una latencia máxima de 273ms y un percentil 95 dentro del umbral aceptable (<500ms). La arquitectura actual demuestra un buen nivel de rendimiento y estabilidad.”

---
