USE defaultdb;

CREATE TABLE usuarios (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE sabores (
    sabor_id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    fecha_creacion DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id) ON DELETE CASCADE
);

CREATE TABLE produccion (
    produccion_id INT PRIMARY KEY AUTO_INCREMENT,
    sabor_id INT NOT NULL,
    fecha_produccion DATE NOT NULL,
    cantidad_kg DECIMAL(8,2) NOT NULL,
    responsable VARCHAR(100) NOT NULL,
    costo DECIMAL(10,2),
    notas TEXT,
    FOREIGN KEY (sabor_id) REFERENCES sabores(sabor_id) ON DELETE CASCADE
);


CREATE TABLE pedidos (
    pedido_id INT PRIMARY KEY AUTO_INCREMENT,
    sabor_id INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado ENUM('pendiente', 'entregado', 'cancelado') DEFAULT 'pendiente',
    FOREIGN KEY (sabor_id) REFERENCES sabores(sabor_id) ON DELETE CASCADE
);

INSERT INTO usuarios (nombre_completo, email, password) VALUES
('Admin Heladería', 'lshl@heladeria.com', '12345'),
('Empleado Prueba', 'empleado@heladeria.com', '12345');
