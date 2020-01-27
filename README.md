# Proyecto JDBC
Proyecto desarrollado en Java que hace uso del conector JDBC conectando a una base de datos MySQL.
El proyecto ejecuta un menú que permite realizar operaciones básicas sobre la base de datos.

## Script de la base de datos

```sql
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `clientes` (
  `id` int(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL
)

CREATE TABLE `coches` (
  `id` int(11) NOT NULL,
  `marca` varchar(255) NOT NULL,
  `modelo` varchar(255) NOT NULL,
  `matricula` varchar(255) NOT NULL,
  `fecha_matriculacion` date NOT NULL,
  `color` varchar(255) NOT NULL,
  `hibrido` tinyint(1) NOT NULL,
  `precio` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `coches` (`id`, `marca`, `modelo`, `matricula`, `fecha_matriculacion`, `color`, `hibrido`, `precio`) VALUES
(1, 'Renault', 'Modelo1', '5678DEF', '2001-01-01', 'blanco', 1, 35000),
(2, 'Peugeot', 'Modelo1', '1234ABC', '2001-01-01', 'gris', 0, 2500),
(3, 'Volvo', 'Modelo1', '5678DEF', '2001-01-01', 'blanco', 1, 35000);

ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `coches`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `clientes`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

ALTER TABLE `coches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;
```
