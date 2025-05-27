-- Desactivar restricciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

-- Crear y ejecutar una sentencia por tabla
-- Puedes copiar y ejecutar esto directamente

TRUNCATE TABLE ordenanza;
TRUNCATE TABLE recibos;
TRUNCATE TABLE vehiculos;
TRUNCATE TABLE contribuyente;

-- Activar de nuevo restricciones
SET FOREIGN_KEY_CHECKS = 1;
