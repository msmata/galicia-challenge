import http from 'k6/http';
import { sleep } from 'k6';
import { check } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 20 },  // Fase de subida
    { duration: '30s', target: 20 },  // Carga sostenida
    { duration: '10s', target: 0 },   // Fase de bajada
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% de las solicitudes deben durar < 500ms
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
    let resToken = http.get(`${BASE_URL}/auth/login/`, {
        body: {
            username: "user123",
            password: "pass123"
        }
    });

    const token = `Bearer ${resToken.token}`;

    let res = http.get(`${BASE_URL}/carts/user/`, {
        headers: { Authorization: token },
    });

    check(res, {
        'status es 200': (r) => r.status === 200,
    });

    sleep(1); // espera de 1 segundo entre solicitudes (simula usuario real)
}
