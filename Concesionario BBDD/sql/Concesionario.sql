--
-- Creación de la BB.DD. "CONCESIONARIO"
-- 
CREATE DATABASE CONCESIONARIO /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */;
USE CONCESIONARIO;
--
-- Tabla COCHES
--
CREATE TABLE COCHES (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    MATRICULA VARCHAR(7) NOT NULL, 
    MARCA VARCHAR(25) NOT NULL,
    MODELO VARCHAR(50) NOT NULL,
    COLOR VARCHAR(25) NOT NULL,
    UNIQUE (MATRICULA)
);
--
-- Tabla PASAJEROS
-- Relación (0 -> N) con COCHES.
--
CREATE TABLE PASAJEROS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NOMBRE VARCHAR(100) NOT NULL,
    EDAD INT(3) NOT NULL,
    PESO FLOAT NOT NULL,
    ID_COCHE INT,
	FOREIGN KEY (ID_COCHE) REFERENCES COCHES(ID),
    UNIQUE (NOMBRE)
);
--
-- Insertamos varios coches de muestra.
--
INSERT INTO COCHES (MATRICULA, MARCA, MODELO, COLOR) VALUES
('2839LPD', 'FORD', 'MUSTANG', 'ROJO');
INSERT INTO COCHES (MATRICULA, MARCA, MODELO, COLOR) VALUES
('0284DMY', 'SEAT', 'LEÓN', 'BLANCO');
INSERT INTO COCHES (MATRICULA, MARCA, MODELO, COLOR) VALUES
('8273KHW', 'RENAULT', 'CLIO', 'AZUL');
INSERT INTO COCHES (MATRICULA, MARCA, MODELO, COLOR) VALUES
('1293BPS', 'CITROEN', 'C4', 'NEGRO');
--
-- Insertamos varios pasajeros de muestra.
-- 
INSERT INTO PASAJEROS (NOMBRE, EDAD, PESO) VALUES
('RAMÓN RODRÍGUEZ SÁNCHEZ', 25, 72.5);
INSERT INTO PASAJEROS (NOMBRE, EDAD, PESO) VALUES
('JOSÉ PÉREZ MARTÍN', 45, 88.0);
INSERT INTO PASAJEROS (NOMBRE, EDAD, PESO) VALUES
('EVA SANTOLARIA FERNÁNDEZ', 34, 47.3);
INSERT INTO PASAJEROS (NOMBRE, EDAD, PESO) VALUES
('MARIA DELGADO NADAL', 42, 58.6);
INSERT INTO PASAJEROS (NOMBRE, EDAD, PESO) VALUES
('IGNACIO PINEDA MARTÍN', 29, 61.3);
--
COMMIT;