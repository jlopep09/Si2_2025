-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         11.6.2-MariaDB-ubu2404 - mariadb.org binary distribution
-- SO del servidor:              debian-linux-gnu
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para coches
CREATE DATABASE IF NOT EXISTS `coches` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci */;
USE `coches`;

-- Volcando estructura para tabla coches.contribuyente
CREATE TABLE IF NOT EXISTS `contribuyente` (
  `idContribuyente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido1` varchar(50) NOT NULL,
  `apellido2` varchar(50) DEFAULT NULL,
  `nifnie` varchar(50) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `numero` varchar(50) DEFAULT NULL,
  `paisCCC` varchar(50) DEFAULT NULL,
  `CCC` varchar(50) DEFAULT NULL,
  `IBAN` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `bonificacion` double DEFAULT NULL,
  `ayuntamiento` varchar(50) NOT NULL,
  PRIMARY KEY (`idContribuyente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla coches.ordenanza
CREATE TABLE IF NOT EXISTS `ordenanza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ayuntamiento` varchar(50) NOT NULL,
  `tipoVehiculo` varchar(50) NOT NULL,
  `unidad` varchar(50) NOT NULL,
  `minimo_rango` double NOT NULL,
  `maximo_rango` double NOT NULL,
  `importe` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla coches.recibos
CREATE TABLE IF NOT EXISTS `recibos` (
  `numRecibo` int(11) NOT NULL AUTO_INCREMENT,
  `fechaPadron` date NOT NULL,
  `fechaRecibo` date NOT NULL,
  `nifContribuyente` varchar(50) NOT NULL,
  `direccionCompleta` varchar(50) NOT NULL,
  `IBAN` varchar(50) DEFAULT NULL,
  `tipoVehiculo` varchar(50) NOT NULL,
  `marcaModelo` varchar(50) NOT NULL,
  `unidad` varchar(50) NOT NULL,
  `valorUnidad` double NOT NULL,
  `totalRecibo` double NOT NULL,
  `exencion` char(1) DEFAULT NULL,
  `bonificacion` char(1) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `ayuntamiento` varchar(50) NOT NULL,
  `idContribuyente` int(11) NOT NULL,
  `idVehiculo` int(11) NOT NULL,
  PRIMARY KEY (`numRecibo`),
  KEY `FK_recibos_contribuyente` (`idContribuyente`),
  KEY `FK_recibos_vehiculos` (`idVehiculo`),
  CONSTRAINT `FK_recibos_contribuyente` FOREIGN KEY (`idContribuyente`) REFERENCES `contribuyente` (`idContribuyente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_recibos_vehiculos` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculos` (`idVehiculo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla coches.vehiculos
CREATE TABLE IF NOT EXISTS `vehiculos` (
  `idVehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `matricula` varchar(50) NOT NULL,
  `numeroBastidor` varchar(50) NOT NULL,
  `caballosFiscales` double DEFAULT NULL,
  `plazas` double DEFAULT NULL,
  `centimetroscubicos` double DEFAULT NULL,
  `kgcarga` double DEFAULT NULL,
  `exencion` char(1) DEFAULT NULL,
  `fechaMatriculacion` date NOT NULL,
  `fechaAlta` date NOT NULL,
  `fechaBaja` date DEFAULT NULL,
  `fechaBajaTemporal` date DEFAULT NULL,
  `idContribuyente` int(11) NOT NULL,
  `idOrdenanza` int(11) NOT NULL,
  PRIMARY KEY (`idVehiculo`),
  KEY `FK_vehiculos_contribuyente` (`idContribuyente`),
  KEY `FK_vehiculos_ordenanza` (`idOrdenanza`),
  CONSTRAINT `FK_vehiculos_contribuyente` FOREIGN KEY (`idContribuyente`) REFERENCES `contribuyente` (`idContribuyente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vehiculos_ordenanza` FOREIGN KEY (`idOrdenanza`) REFERENCES `ordenanza` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
