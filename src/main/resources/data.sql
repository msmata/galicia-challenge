INSERT INTO PRODUCT (code, name, price, category) VALUES ('80a35bf16dfa', 'Mouse', 1200.0, 'tecnology');
INSERT INTO PRODUCT (code, name, price, category) VALUES ('2fb1312f7100', 'Teclado', 2500.0, 'tecnology');
INSERT INTO PRODUCT (code, name, price, category) VALUES ('9c77a5833f47', 'Martin Fierro', 15000.0, 'libros');

INSERT INTO discount (category, percent) VALUES ('tecnology', 0.10);
INSERT INTO discount (category, percent) VALUES ('libros', 0.05);

INSERT INTO users (user_id, password) VALUES ('user123', 'pass123');
INSERT INTO users (user_id, password) VALUES ('admin', 'adminpass');