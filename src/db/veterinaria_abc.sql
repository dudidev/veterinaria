CREATE DATABASE IF NOT EXISTS veterinaria_abc;
USE veterinaria_abc;

CREATE TABLE persona (
    documento VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    raza VARCHAR(50),
    sexo VARCHAR(10),
    comentarios TEXT,
    documento_dueno VARCHAR(20),
    FOREIGN KEY (documento_dueno) REFERENCES persona(documento) ON DELETE CASCADE
);