-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.32 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando datos para la tabla ivtm.contribuyente: ~98 rows (aproximadamente)
DELETE FROM `contribuyente`;
INSERT INTO `contribuyente` (`idContribuyente`, `nombre`, `apellido1`, `apellido2`, `nifnie`, `direccion`, `numero`, `paisCCC`, `CCC`, `IBAN`, `email`, `bonificacion`, `ayuntamiento`) VALUES
	(2846, 'Juan', 'Martinez', 'Dominguez', '09677930J', 'MOLINO (EL)', '0013', 'DK', '31645124473461205164', 'ES2131645124473461205164', 'Martinez.Dominguez.Juan@unileon.es', 0, 'BENAVIDES'),
	(2847, 'Ruben Dario', 'Fuertes', 'Montiel', '09683488M', 'PRADERA (LA)', '0004', 'ES', '65614874165615445616', 'ES2265614874165615445616', 'Fuertes.Montiel.Ruben Dario@hotmail.com', 0, 'BENAVIDES'),
	(2848, 'Alencar', 'Lidiane', 'Morala', '09685674Y', 'PRADERA (LA)', '0004', 'RO', '32569523016220165156', 'ES2332569523016220165156', 'Lidiane.Morala.Alencar@yahoo.es', 0, 'BENAVIDES'),
	(2849, 'Alencar', 'Lidiane', 'Nuñez', '09685694A', 'MADRID', '0011', 'DE', '24561937521546497521', 'ES2424561937521546497521', 'Lidiane.Nunez.Alencar@jcyl.es', 0, 'BENAVIDES'),
	(2850, 'Rocio', 'Gil', 'Olle', '09688492H', 'CAMINO DE SANTIAGO', '0028', 'MC', '36520125638451012515', 'ES2536520125638451012515', 'Gil.Olle.Rocio@unileon.es', 0, 'BENAVIDES'),
	(2851, 'Soledad', 'Diez', 'Orejas', '09693306W', 'PEÑA UBIÑA', '0188', 'ES', '21584976902154655487', 'ES2621584976902154655487', 'Diez.Orejas.Soledad@hotmail.com', 0, 'BENAVIDES'),
	(2852, 'Vittorio', 'Diez', 'Otero', '09699481J', 'IGLESIA (LA)', '0003', 'GR', '20125003305201112544', 'ES2720125003305201112544', 'Diez.Otero.Vittorio@yahoo.es', 0, 'BENAVIDES'),
	(2853, 'Vittorio', 'Baglione', 'Pardo', '09701763H', 'ABAJO', '0003', 'ES', '21651484690980008984', 'ES2821651484690980008984', 'Baglione.Pardo.Vittorio@jcyl.es', 0, 'BENAVIDES'),
	(2854, 'Adolfo', 'Blanco', 'Perez', '09703447T', 'MONTE (EL)', '0043', 'FI', '20960043043554600000', 'ES2920960043043554600000', 'Blanco.Perez.Adolfo@unileon.es', 0, 'BENAVIDES'),
	(2855, 'Mauro', 'Aparicio', 'Perez', '09706119G', 'MONTE (EL)', '0050', 'ES', '21564975243245467995', 'ES3021564975243245467995', 'Aparicio.Perez.Mauro@hotmail.com', 0, 'BENAVIDES'),
	(2856, 'Agustin', 'Castro', 'Presa', '09706476Q', 'MONTE (EL)', '0050', 'LT', '32566221522587754554', 'ES3132566221522587754554', 'Castro.Presa.Agustin@yahoo.es', 0, 'BENAVIDES'),
	(2857, 'Luis', 'Benito', 'Prieto', '09707275X', 'MONTE (EL)', '0050', 'EE', '23215465315456411515', 'ES3223215465315456411515', 'Benito.Prieto.Luis@jcyl.es', 0, 'BENAVIDES'),
	(2858, 'Armando', 'Garcia', 'Prieto', '09710840X', 'IGLESIA (LA)', '0007', 'BE', '00750184310702510000', 'ES3300750184310702510000', 'Garcia.Prieto.Armando@unileon.es', 0, 'BENAVIDES'),
	(2859, 'Latifa Epfouad Erre', 'Boughaza', 'Robles', '09711691X', 'LEON-SANTANDER', '0027', 'SM', '25894363475485700145', 'ES3525894363475485700145', 'Boughaza.Robles.Latifa Epfouad Erre@yahoo.es', 0, 'BENAVIDES'),
	(2860, 'Saaida', 'Boughaza', 'Robles', '09713006Z', 'REAL', '0017', 'ES', '96431245118150005156', 'ES3696431245118150005156', 'Boughaza.Robles.Saaida@jcyl.es', 0, 'BENAVIDES'),
	(2861, 'Antonio', 'Garcia', 'Rodriguez', '09713007S', 'REAL', '0017', 'AT', '25030000114574745458', 'ES3725030000114574745458', 'Garcia.Rodriguez.Antonio@unileon.es', 0, 'BENAVIDES'),
	(2862, 'Dorina', 'Gonzalez', 'Rodriguez', '09713528F', 'LEON-SANTANDER', '0027', 'IT', '15953684811254695203', 'ES3815953684811254695203', 'Gonzalez.Rodriguez.Dorina@hotmail.com', 0, 'BENAVIDES'),
	(2863, 'Pilar', 'Gonzalez', 'Salas', '09715064W', 'LEON-SANTANDER', '0027', 'ES', '20960043023096200000', 'ES3920960043023096200000', 'Gonzalez.Salas.Pilar@yahoo.es', 0, 'BENAVIDES'),
	(2864, 'Clementina', 'Iglesias', 'Sanchez', '09715167J', 'REAL', '0017', 'DK', '00750184310702510000', 'ES4000750184310702510000', 'Iglesias.Sanchez.Clementina@jcyl.es', 0, 'BENAVIDES'),
	(2865, 'Miguel angel', 'Quintanilla', 'Fernandez', '09715890T', 'MILGAS (LAS)', '0001', 'ES', '23455254943263234457', 'ES4123455254943263234457', 'Quintanilla..Miguel angel@unileon.es', 0, 'BENAVIDES'),
	(2866, 'Blas', 'Vega', 'Martinez', '09716184H', 'MONTE (EL)', '0041', 'GR', '20910936583000000000', 'ES4220910936583000000000', 'Vega..Blas@hotmail.com', 0, 'BENAVIDES'),
	(2867, 'Pedro', 'Portugues', 'Rodriguez', '09717650N', 'MONTE (EL)', '0017', 'ES', '20960043032159000000', 'ES4320960043032159000000', 'Portugues..Pedro@yahoo.es', 0, 'BENAVIDES'),
	(2868, 'Paulina', 'Cepin', 'Sanchez', '09718644V', 'REAL', '0016', 'DE', '12669681115112121210', 'ES4412669681115112121210', 'Cepin..Paulina@jcyl.es', 0, 'BENAVIDES'),
	(2869, 'Gerardo', 'Martinez', 'Molina', '09720049L', 'MONTE (EL)', '0017', 'ES', '56187775315550000651', 'ES4656187775315550000651', 'Martinez.Molina.Gerardo@hotmail.com', 0, 'BENAVIDES'),
	(2870, 'Cecilia', 'Garcia', 'Montiel', '09720969L', 'PEÑA UBIÑA', '0188', 'ES', '25516848021156151054', 'ES4725516848021156151054', 'Garcia.Montiel.Cecilia@yahoo.es', 0, 'BENAVIDES'),
	(2871, 'Genaro', 'Moncada', 'Morala', '09722535K', 'IGLESIA (LA)', '0007', 'PT', '64578946740051516490', 'ES4864578946740051516490', 'Moncada.Morala.Genaro@jcyl.es', 0, 'BENAVIDES'),
	(2872, 'Clara', 'Sanchez', 'Nuñez', '09722591P', 'IGLESIA (LA)', '0007', 'ES', '34698752714600549403', 'ES4934698752714600549403', 'Sanchez.Nunez.Clara@unileon.es', 0, 'BENAVIDES'),
	(2873, 'Ana Belen', 'Lombas', 'Olle', '09722827Z', 'IGLESIA (LA)', '0007', 'ES', '66649444162310000255', 'ES5066649444162310000255', 'Lombas.Olle.Ana Belen@hotmail.com', 0, 'BENAVIDES'),
	(2874, 'Generoso', 'Martin', 'Martin', '09723206W', 'PENILLA', '0006', 'FR', '23185484465641685100', 'ES5123185484465641685100', 'Martin.Martin.Generoso@yahoo.es', 0, 'BENAVIDES'),
	(2875, 'Tomas', 'Cedron', 'Perez', '09731953D', 'MONTE (EL)', '0041', 'DE', '21508149175421346497', 'ES5521508149175421346497', 'Cedron.Perez.Tomas@yahoo.es', 0, 'BENAVIDES'),
	(2876, 'Clotilde', 'Castro', 'Presa', '09733795B', 'VALLEO (EL)', '0004', 'DE', '21346154503164978451', 'ES5621346154503164978451', 'Castro.Presa.Clotilde@jcyl.es', 0, 'BENAVIDES'),
	(2877, 'Cristina', 'Alonso', 'Prieto', '09734839C', 'MILGAS (LAS)', '0001', 'ES', '25187786311225455548', 'ES5725187786311225455548', 'Alonso.Prieto.Cristina@unileon.es', 0, 'BENAVIDES'),
	(2878, 'Sara', 'Florez', 'Prieto', '09736219C', 'MONTE (EL)', '0034', 'ES', '23164897642213030615', 'ES5823164897642213030615', 'Florez.Prieto.Sara@hotmail.com', 0, 'BENAVIDES'),
	(2879, 'Encarnacion', 'Lopez', 'Riverto', '09737592J', 'PINOS (LOS)', '0254', 'ES', '96536214865214585214', 'ES5996536214865214585214', 'Lopez.Riverto.Encarnacion@yahoo.es', 0, 'BENAVIDES'),
	(2880, 'Dolores', 'Lopez', 'Robles', '09737631Y', 'PINOS (LOS)', '0254', 'ES', '85461325251978750005', 'ES6085461325251978750005', 'Lopez.Robles.Dolores@jcyl.es', 0, 'BENAVIDES'),
	(2881, 'Herminia', 'Almanzar', 'Robles', '09738099Z', 'PINOS (LOS)', '0254', 'FI', '24587946032003165464', 'ES6124587946032003165464', 'Almanzar.Robles.Herminia@unileon.es', 0, 'BENAVIDES'),
	(2882, 'Sonia', 'Pisabarro', 'Rodriguez', '09752292Q', 'NAVAS (LAS)', '0000', 'ES', '20960043073071400000', 'ES6220960043073071400000', 'Pisabarro.Rodriguez.Sonia@hotmail.com', 0, 'BENAVIDES'),
	(2883, 'Generoso', 'Pisabarro', 'Rodriguez', '09752293V', 'RESIDENCIAL LAS FUENTES', '0018', 'ES', '20960043042158800000', 'ES6320960043042158800000', 'Pisabarro.Rodriguez.Generoso@yahoo.es', 0, 'BENAVIDES'),
	(2884, 'Sergio', 'Alaiz', 'Salas', '09753101C', 'TELENO', '0196', 'ES', '21654587985156484454', 'ES6421654587985156484454', 'Alaiz.Salas.Sergio@jcyl.es', 0, 'BENAVIDES'),
	(2885, 'Bonifacio', 'Fresco', 'Sanchez', '09754339Q', 'RIO PORMA', '0136', 'ES', '51651681961210656510', 'ES6551651681961210656510', 'Fresco.Sanchez.Bonifacio@unileon.es', 0, 'BENAVIDES'),
	(2886, 'Demetrio', 'Fernandez', 'Garcia', '09757444Q', 'RIO ESLA', '0115', 'ES', '66552211148855332200', 'ES6666552211148855332200', 'Fernandez.Garcia.Demetrio@hotmail.com', 0, 'BENAVIDES'),
	(2887, 'German', 'Martinez', 'Garcia', '09757564K', 'IGLESIA (LA)', '0007', 'GB', '20910936583000000000', 'ES6720910936583000000000', 'Martinez.Garcia.German@yahoo.es', 0, 'BENAVIDES'),
	(2888, 'Marta', 'Lopez', 'Getino', '09762197P', 'GENERAL', '0070', 'DE', '01821135910205540000', 'ES6801821135910205540000', 'Lopez.Getino.Marta@jcyl.es', 0, 'BENAVIDES'),
	(2889, 'Israel', 'Amo', 'Gomara', '09762293N', 'VALLE (EL)', '0006', 'DE', '22631245526916432102', 'ES6922631245526916432102', 'Amo.Gomara.Israel@unileon.es', 0, 'BENAVIDES'),
	(2890, 'Israel', 'Amo', 'Gonzalez', '09763856B', 'CONFORCOS', '0011', 'ES', '20960043043075700000', 'ES7020960043043075700000', 'Amo.Gonzalez.Israel@hotmail.com', 0, 'BENAVIDES'),
	(2891, 'Gabriel', 'Prieto', 'Gonzalez', '09768995K', 'VALLE (EL)', '0006', 'SM', '25635478321002541225', 'ES7125635478321002541225', 'Prieto.Gonzalez.Gabriel@yahoo.es', 0, 'BENAVIDES'),
	(2892, 'Ruben Dario', 'Garcia', 'Gracia', '09770039F', 'VALLE (EL)', '0006', 'ES', '32154697195423121000', 'ES7232154697195423121000', 'Garcia.Gracia.Ruben Dario@jcyl.es', 0, 'BENAVIDES'),
	(2893, 'Alencar', 'Pasado', 'Guerra', '09774163Z', 'LA ERA', '0002', 'GR', '36521452736500658485', 'ES7336521452736500658485', 'Pasado.Guerra.Alencar@unileon.es', 0, 'BENAVIDES'),
	(2894, 'Alencar', 'Fresco', 'Gutierrez', '09774199G', 'LA ERA', '0002', 'GB', '20008521528775113366', 'ES7420008521528775113366', 'Fresco.Gutierrez.Alencar@hotmail.com', 0, 'BENAVIDES'),
	(2895, 'Adolfo', 'Puerto', 'Marquez', '09778683A', 'CALLEJA (LA)', '0001', 'ES', '20960043033000100000', 'ES7920960043033000100000', 'Puerto.Marquez.Adolfo@yahoo.es', 0, 'BENAVIDES'),
	(2896, 'Giovani', 'Moran', 'Martinez', '09778772T', 'CAMINO DE SANTIAGO', '0024', 'GB', '36585214290025478551', 'ES8036585214290025478551', 'Moran.Martinez.Giovani@jcyl.es', 0, 'BENAVIDES'),
	(2897, 'Agustin', 'Lopez', 'Martinez', '09779269Z', 'GENERAL', '0001', 'ES', '12548523465214585214', 'ES8112548523465214585214', 'Lopez.Martinez.Agustin@unileon.es', 0, 'BENAVIDES'),
	(2898, 'Luis', 'Domingo', 'Melcon', '09780389F', 'REAL', '0003', 'ES', '31624561042546920007', 'ES8231624561042546920007', 'Domingo.Melcon.Luis@hotmail.com', 0, 'BENAVIDES'),
	(2899, 'Armando', 'Dominguez', 'Garcia', '09780996Q', 'VALLE (EL)', '0002', 'ES', '36154231712500312566', 'ES8336154231712500312566', 'Dominguez..Armando@yahoo.es', 0, 'BENAVIDES'),
	(2900, 'Latifa', 'Ramos', 'Fernandez', '09781067H', 'REAL', '0016', 'ES', '44875664127231645789', 'ES8444875664127231645789', 'Ramos..Latifa@jcyl.es', 0, 'BENAVIDES'),
	(2901, 'Latifa Epfouad Erre', 'Cadenas', 'Gonzalez', '09781327W', 'SIN SALIDA', '0001', 'ES', '20960031442124800000', 'ES8520960031442124800000', 'Cadenas..Latifa Epfouad Erre@unileon.es', 0, 'BENAVIDES'),
	(2902, 'Saaida', 'Blanco', 'Garcia', '09782302B', 'CONFORCOS', '0007', 'ES', '33620012937852100256', 'ES8633620012937852100256', 'Blanco.Garcia.Saaida@hotmail.com', 0, 'BENAVIDES'),
	(2903, 'Antonio', 'Fernandez', 'Garcia', '09782419J', 'CONFORCOS', '0003', 'ES', '33218885441445121022', 'ES8733218885441445121022', 'Fernandez.Garcia.Antonio@yahoo.es', 0, 'BENAVIDES'),
	(2904, 'Dorina', 'Garcia', 'Getino', '09783952M', 'MONTE (EL)', '0046', 'ES', '62581542713690044508', 'ES8862581542713690044508', 'Garcia.Getino.Dorina@jcyl.es', 0, 'BENAVIDES'),
	(2905, 'Rocio', 'Otero', 'Gomara', '09808127F', 'SENDA (LA)', '0002', 'ES', '25165151118666365100', 'ES8925165151118666365100', 'Otero.Gomara.Rocio@unileon.es', 0, 'BENAVIDES'),
	(2906, 'Soledad', 'Otero', 'Gonzalez', '09786577P', 'SIN SALIDA', '0001', 'ES', '20960043033000100000', 'ES9020960043033000100000', 'Otero.Gonzalez.Soledad@hotmail.com', 0, 'BENAVIDES'),
	(2907, 'Vittorio', 'Vara', 'Gonzalez', '09787319Z', 'VALLE (EL)', '0002', 'PT', '36952365020014425254', 'ES9136952365020014425254', 'Vara.Gonzalez.Vittorio@yahoo.es', 0, 'BENAVIDES'),
	(2908, 'Vittorio', 'Martines', 'Gracia', '09789574S', 'SENDA (LA)', '0003', 'ES', '65168874641561561500', 'ES9265168874641561561500', 'Martines.Gracia.Vittorio@jcyl.es', 0, 'BENAVIDES'),
	(2909, 'Mario', 'Bernal', 'Guerra', '09789575Q', 'MADRID', '0017', 'ES', '20960583831234500000', 'ES9320960583831234500000', 'Bernal.Guerra.Mario@unileon.es', 0, 'BENAVIDES'),
	(2910, 'Concepcion', 'Blanco', 'Gutierrez', '09790982C', 'CONFORCOS', '0011', 'ES', '21416325811510005514', 'ES9421416325811510005514', 'Blanco.Gutierrez.Concepcion@hotmail.com', 0, 'BENAVIDES'),
	(2911, 'Maria', 'Sutil', 'Herreras', '09791120C', 'SIN SALIDA', '0001', 'LU', '32628484504115151115', 'ES9532628484504115151115', 'Sutil.Herreras.Maria@yahoo.es', 0, 'BENAVIDES'),
	(2912, 'Federico', 'Diez', 'Laiz', '09791323Q', 'SIN SALIDA', '0001', 'ES', '20960056163231500000', 'ES9620960056163231500000', 'Diez.Laiz.Federico@jcyl.es', 0, 'BENAVIDES'),
	(2913, 'Serafin', 'De la fuente', 'Manzanares', '09798076F', 'CONFORCOS', '0007', 'ES', '63516541828944000984', 'ES9863516541828944000984', 'De la fuente.Manzanares.Serafin@hotmail.com', 0, 'BENAVIDES'),
	(2914, 'Eulalia', 'Garcia', 'Marquez', '09798287B', 'CONFORCOS', '0015', 'ES', '23658965214585223202', 'ES9923658965214585223202', 'Garcia.Marquez.Eulalia@yahoo.es', 0, 'BENAVIDES'),
	(2915, 'Gonzalo', 'Perez', 'Martinez', '09799247M', 'VIÑA (LA)', '0007', 'FI', '32658012367712548745', 'ES1132658012367712548745', 'Perez.Martinez.Gonzalo@jcyl.es', 0, 'BENAVIDES'),
	(2916, 'Emiliano', 'Alvarez', 'Martinez', '09800420M', 'PEÑA SANTA', '0187', 'ES', '23652365142254222000', 'ES1223652365142254222000', 'Alvarez.Martinez.Emiliano@unileon.es', 0, 'BENAVIDES'),
	(2917, 'Melisa', 'Marin', 'Melcon', '09800921T', 'SENDA (LA)', '0002', 'FR', '20012541100023365233', 'ES1320012541100023365233', 'Marin.Melcon.Melisa@hotmail.com', 0, 'BENAVIDES'),
	(2918, 'Montserrat', 'Garcia', 'Fernandez', '09801107W', 'PLAZUELA (LA)', '0005', 'ES', '32584216971684051000', 'ES1432584216971684051000', 'Garcia..Montserrat@yahoo.es', 0, 'BENAVIDES'),
	(2919, 'Jose Antonio', 'Alonso', 'Cabero', '09802657B', 'POZO (EL)', '0006', 'ES', '95485212315484010000', 'ES1695485212315484010000', 'Alonso.Cabero.Jose Antonio@unileon.es', 0, 'BENAVIDES'),
	(2920, 'Bernardo', 'Dominguez', 'Cabeza', '09803456M', 'POZO (EL)', '0006', 'LT', '21856333126985542360', 'ES1721856333126985542360', 'Dominguez.Cabeza.Bernardo@hotmail.com', 0, 'BENAVIDES'),
	(2921, 'Nelida', 'Garcia', 'Calleja', '09804256T', 'GENERAL', '0043', 'ES', '36245978133245679001', 'ES1836245978133245679001', 'Garcia.Calleja.Nelida@yahoo.es', 0, 'BENAVIDES'),
	(2922, 'Marisa', 'Labarga', 'Candanedo', '09804568J', 'GENERAL', '0043', 'ES', '31245164156597845124', 'ES1931245164156597845124', 'Labarga.Candanedo.Marisa@jcyl.es', 0, 'BENAVIDES'),
	(2923, 'Marta', 'Herrero', 'Garcia', '09806108N', 'RIO TORIO', '0060', 'SM', '23221158252545471411', 'ES2023221158252545471411', 'Herrero..Marta@unileon.es', 0, 'BENAVIDES'),
	(2924, 'Miguel', 'Lopez', 'Carro', '09807895M', 'HUERTOS (LOS)', '0001', 'SE', '32574512085411002255', 'ES2132574512085411002255', 'Lopez.Carro.Miguel@hotmail.com', 0, 'BENAVIDES'),
	(2925, 'Cruz', 'Fernandez', 'Cascallana', '09807896Y', 'FUENTE (LA)', '0008', 'ES', '20960043013468900000', 'ES2220960043013468900000', 'Fernandez.Cascallana.Cruz@yahoo.es', 0, 'BENAVIDES'),
	(2926, 'Carmen', 'Gomez', 'Castañeda', '09808038X', 'VIÑA (LA)', '0009', 'ES', '31215643855060225021', 'ES2331215643855060225021', 'Gomez.Castaneda.Carmen@jcyl.es', 0, 'BENAVIDES'),
	(2927, 'Hugo', 'Sanchez', 'Castro', '09808051T', 'DOCTOR RODRIGUEZ GUISASOL', '0022', 'AT', '85550564726165145610', 'ES2485550564726165145610', 'Sanchez.Castro.Hugo@unileon.es', 0, 'BENAVIDES'),
	(2928, 'Khadouj', 'Sanchez', 'Castro', '09808061X', 'NUESTRA SEÑORA DEL CAMINO', '0099', 'ES', '65165654918886005001', 'ES2565165654918886005001', 'Sanchez.Castro.Khadouj@hotmail.com', 0, 'BENAVIDES'),
	(2929, 'Mario', 'Herrero', 'Coello ', '10160508M', 'REAL', '0097', 'AT', '65645150005168448896', 'ES3265645150005168448896', 'Herrero.Coello .Mario@unileon.es', 0, 'BENAVIDES'),
	(2930, 'Concepcion', 'Lopez', 'De la puente', '10178996R', 'ROSALES (LOS)', '0028', 'IT', '26551681877651415636', 'ES3326551681877651415636', 'Lopez.De la puente.Concepcion@hotmail.com', 0, 'BENAVIDES'),
	(2931, 'Maria', 'Fernandez', 'Del amo', '10193861P', 'MANZANALES (LOS)', '0004', 'HU', '99558741836555551120', 'ES3499558741836555551120', 'Fernandez.Del amo.Maria@yahoo.es', 0, 'BENAVIDES'),
	(2932, 'Federico', 'Gomez', 'Diez', '10198570W', 'REGUERO (EL)', '0002', 'ES', '52198484752100515144', 'ES3552198484752100515144', 'Gomez.Diez.Federico@jcyl.es', 0, 'BENAVIDES'),
	(2933, 'Gabriel', 'Moncada', 'Martinez', '11398830F', 'REGUERO (EL)', '0002', 'IE', '51556584121251000254', 'ES3651556584121251000254', 'Moncada.Martinez.Gabriel@unileon.es', 0, 'BENAVIDES'),
	(2934, 'Ismael', 'Vara', 'Cembranos', '11437269J', 'ERAS (LAS)', '0014', 'DK', '32541112811220000588', 'ES5732541112811220000588', 'Vara.Cembranos.Ismael@jcyl.es', 0, 'BENAVIDES'),
	(2935, 'Diana', 'Martines', 'Centeno', '11637421L', 'JUAN CARLOS I', '0010', 'LT', '62541122001110105611', 'ES5862541122001110105611', 'Martines.Centeno.Diana@unileon.es', 0, 'BENAVIDES'),
	(2936, 'Elisa', 'Bernal', 'Cerro', '11966672W', 'JUAN CARLOS I', '0012', 'ES', '55065688761051056105', 'ES5955065688761051056105', 'Bernal.Cerro.Elisa@hotmail.com', 0, 'BENAVIDES'),
	(2937, 'Nelida', 'Blanco', 'Chamorro', '11662128R', 'ERAS (LAS)', '0003', 'ES', '26221011628048788896', 'ES6026221011628048788896', 'Blanco.Chamorro.Nelida@yahoo.es', 0, 'BENAVIDES'),
	(2938, 'Marisa', 'Sutil', 'Charro', '11673213T', 'JUAN CARLOS I', '0026', 'ES', '12548521518742146695', 'ES6112548521518742146695', 'Sutil.Charro.Marisa@jcyl.es', 0, 'BENAVIDES'),
	(2939, 'Marta', 'Diez', 'Chen', '11942643P', 'JUAN CARLOS I', '0026', 'ES', '01826530120201560000', 'ES6201826530120201560000', 'Diez.Chen.Marta@unileon.es', 0, 'BENAVIDES'),
	(2940, 'Miguel', 'Fernandez', 'Coello ', '11971207Y', 'COTANO', '0008', 'ES', '21651651812511133551', 'ES6321651651812511133551', 'Fernandez.Coello .Miguel@hotmail.com', 0, 'BENAVIDES'),
	(2941, 'Cruz', 'De la fuente', 'De la puente', '11957847D', 'ACEBOS (LOS)', '0294', 'ES', '51651487910005118185', 'ES6451651487910005118185', 'De la fuente.De la puente.Cruz@yahoo.es', 0, 'BENAVIDES'),
	(2942, 'Carmen', 'Garcia', 'Del amo', '11959249P', 'MADRID', '0012', 'CZ', '36250012804785523365', 'ES6536250012804785523365', 'Garcia.Del amo.Carmen@jcyl.es', 0, 'BENAVIDES'),
	(2943, 'Hugo', 'Perez', 'Diez', '11959694Q', 'VALDEFRESNO', '0007', 'AT', '22515651915640081000', 'ES6622515651915640081000', 'Perez.Diez.Hugo@unileon.es', 0, 'BENAVIDES');

-- Volcando datos para la tabla ivtm.ordenanza: ~24 rows (aproximadamente)
DELETE FROM `ordenanza`;
INSERT INTO `ordenanza` (`id`, `ayuntamiento`, `tipoVehiculo`, `unidad`, `minimo_rango`, `maximo_rango`, `importe`) VALUES
	(601, 'BENAVIDES', 'TURISMO', 'CABALLOS', 0, 7.99, 20.95),
	(602, 'BENAVIDES', 'TURISMO', 'CABALLOS', 8, 11.99, 58.5),
	(603, 'BENAVIDES', 'TURISMO', 'CABALLOS', 12, 15.99, 128.95),
	(604, 'BENAVIDES', 'TURISMO', 'CABALLOS', 16, 19.99, 167.5),
	(605, 'BENAVIDES', 'TURISMO', 'CABALLOS', 20, 9999, 209.4),
	(606, 'BENAVIDES', 'AUTOBUS', 'PLAZAS', 0, 20, 140.15),
	(607, 'BENAVIDES', 'AUTOBUS', 'PLAZAS', 21, 50, 199.35),
	(608, 'BENAVIDES', 'AUTOBUS', 'PLAZAS', 51, 9999, 240.5),
	(609, 'BENAVIDES', 'CAMION', 'KG', 0, 999, 68),
	(610, 'BENAVIDES', 'CAMION', 'KG', 1000, 2999, 140.15),
	(611, 'BENAVIDES', 'CAMION', 'KG', 3000, 9999, 208.45),
	(612, 'BENAVIDES', 'CAMION', 'KG', 10000, 9999999, 271.85),
	(613, 'BENAVIDES', 'TRACTOR', 'CABALLOS', 0, 15.99, 29.9),
	(614, 'BENAVIDES', 'TRACTOR', 'CABALLOS', 16, 25, 46.9),
	(615, 'BENAVIDES', 'TRACTOR', 'CABALLOS', 25.01, 9999, 140.15),
	(616, 'BENAVIDES', 'REMOLQUE', 'KG', 751, 999, 29.9),
	(617, 'BENAVIDES', 'REMOLQUE', 'KG', 1000, 2999, 46.9),
	(618, 'BENAVIDES', 'REMOLQUE', 'KG', 3000, 9999999, 140.15),
	(619, 'BENAVIDES', 'CICLOMOTOR', 'CC', 0, 49.99, 8.65),
	(620, 'BENAVIDES', 'MOTOCICLETA', 'CC', 0, 125, 8.84),
	(621, 'BENAVIDES', 'MOTOCICLETA', 'CC', 125.01, 250, 15.14),
	(622, 'BENAVIDES', 'MOTOCICLETA', 'CC', 250.01, 500, 30.3),
	(623, 'BENAVIDES', 'MOTOCICLETA', 'CC', 500.01, 1000, 58.5),
	(624, 'BENAVIDES', 'MOTOCICLETA', 'CC', 1000.01, 9999, 121.16);

-- Volcando datos para la tabla ivtm.recibos: ~48 rows (aproximadamente)
DELETE FROM `recibos`;
INSERT INTO `recibos` (`numRecibo`, `fechaPadron`, `fechaRecibo`, `nifContribuyente`, `direccionCompleta`, `IBAN`, `tipoVehiculo`, `marcaModelo`, `unidad`, `valorUnidad`, `totalRecibo`, `exencion`, `bonificacion`, `email`, `ayuntamiento`, `idContribuyente`, `idVehiculo`) VALUES
	(122, '2024-01-01', '2025-02-22', '09722591P', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'MOTOCICLETA', 'YAMAHA GR900', 'CENTIMETROS CUBICOS', 900, 58.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2872, 324),
	(123, '2024-01-01', '2025-02-22', '09677930J', 'MOLINO (EL)0013', 'ES2412345678901234567890', 'TURISMO', 'RENAULT MEGANE', 'CABALLOS FISCALES', 11.64, 58.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2846, 325),
	(124, '2024-01-01', '2025-02-22', '09683488M', 'PRADERA (LA)0004', 'ES2412345678901234567890', 'TURISMO', 'RENAULT ESPACE 2.0', 'CABALLOS FISCALES', 13.29, 128.95, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2847, 326),
	(125, '2024-01-01', '2025-02-22', '09685674Y', 'PRADERA (LA)0004', 'ES2412345678901234567890', 'TURISMO', 'RENAULT CAPTUR', 'CABALLOS FISCALES', 18, 167.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2848, 327),
	(126, '2024-01-01', '2025-02-22', '09685694A', 'MADRID0011', 'ES2412345678901234567890', 'TURISMO', 'OPEL ASTRA', 'CABALLOS FISCALES', 21, 209.4, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2849, 328),
	(127, '2024-01-01', '2025-02-22', '09688492H', 'CAMINO DE SANTIAGO0028', 'ES2412345678901234567890', 'TURISMO', 'KIA SORENTO', 'CABALLOS FISCALES', 25, 209.4, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2850, 329),
	(128, '2024-01-01', '2025-02-22', '09701763H', 'ABAJO0003', 'ES2412345678901234567890', 'AUTOBUS', 'VOLVO F320', 'PLAZAS', 15, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2853, 330),
	(129, '2024-01-01', '2025-02-22', '09703447T', 'MONTE (EL)0043', 'ES2412345678901234567890', 'AUTOBUS', 'MERCEDES X425', 'PLAZAS', 35, 199.35, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2854, 331),
	(130, '2024-01-01', '2025-02-22', '09706119G', 'MONTE (EL)0050', 'ES2412345678901234567890', 'AUTOBUS', 'SCANIA R4787', 'PLAZAS', 65, 240.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2855, 332),
	(131, '2024-01-01', '2025-02-22', '09707275X', 'MONTE (EL)0050', 'ES2412345678901234567890', 'CAMION', 'SCANIA G567', 'KG DE CARGA', 950, 68, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2857, 333),
	(132, '2024-01-01', '2025-02-22', '09710840X', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'CAMION', 'VOLVO X487', 'KG DE CARGA', 2500, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2858, 334),
	(133, '2024-01-01', '2025-02-22', '09711691X', 'LEON-SANTANDER0027', 'ES2412345678901234567890', 'CAMION', 'MERCEDES N364', 'KG DE CARGA', 6500, 208.45, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2859, 335),
	(134, '2024-01-01', '2025-02-22', '09713006Z', 'REAL0017', 'ES2412345678901234567890', 'CAMION', 'SCANIA L758', 'KG DE CARGA', 12500, 271.85, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2860, 336),
	(135, '2024-01-01', '2025-02-22', '09713007S', 'REAL0017', 'ES2412345678901234567890', 'TRACTOR', 'JOHN DEERE C37', 'CABALLOS FISCALES', 14, 29.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2861, 337),
	(136, '2024-01-01', '2025-02-22', '09713528F', 'LEON-SANTANDER0027', 'ES2412345678901234567890', 'TRACTOR', 'JOHN DEERE N91', 'CABALLOS FISCALES', 20, 46.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2862, 338),
	(137, '2024-01-01', '2025-02-22', '09715064W', 'LEON-SANTANDER0027', 'ES2412345678901234567890', 'TRACTOR', 'JOHN DEERE G86', 'CABALLOS FISCALES', 31, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2863, 339),
	(138, '2024-01-01', '2025-02-22', '09715167J', 'REAL0017', 'ES2412345678901234567890', 'REMOLQUE', 'JOHN DEERE F70', 'KG DE CARGA', 850, 29.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2864, 340),
	(139, '2024-01-01', '2025-02-22', '09715890T', 'MILGAS (LAS)0001', 'ES2412345678901234567890', 'REMOLQUE', 'JOHN DEERE F90', 'KG DE CARGA', 1800, 46.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2865, 341),
	(140, '2024-01-01', '2025-02-22', '09716184H', 'MONTE (EL)0041', 'ES2412345678901234567890', 'REMOLQUE', 'JOHN DEERE F120', 'KG DE CARGA', 3250, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2866, 342),
	(141, '2024-01-01', '2025-02-22', '09720049L', 'MONTE (EL)0017', 'ES2412345678901234567890', 'CICLOMOTOR', 'VESPINO F9', 'CENTIMETROS CUBICOS', 49, 8.65, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2869, 343),
	(142, '2024-01-01', '2025-02-22', '09720049L', 'MONTE (EL)0017', 'ES2412345678901234567890', 'MOTOCICLETA', 'YAMAHA MTN850-A', 'CENTIMETROS CUBICOS', 125, 15.14, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2869, 344),
	(143, '2024-01-01', '2025-02-22', '09720969L', 'PEÑA UBIÑA0188', 'ES2412345678901234567890', 'MOTOCICLETA', 'HONDA SCOOTER300', 'CENTIMETROS CUBICOS', 300, 30.3, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2870, 345),
	(144, '2024-01-01', '2025-02-22', '09722535K', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'MOTOCICLETA', 'HONDA CBR600F', 'CENTIMETROS CUBICOS', 600, 58.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2871, 346),
	(145, '2024-01-01', '2025-02-22', '09722827Z', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'MOTOCICLETA', 'YAMAHA X1500', 'CENTIMETROS CUBICOS', 1500, 121.16, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2873, 347),
	(146, '2024-01-01', '2025-02-22', '09677930J', 'MOLINO (EL)0013', 'ES2412345678901234567890', 'TURISMO', 'RENAULT CLIO', 'CABALLOS FISCALES', 9.05, 58.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2846, 348),
	(147, '2024-01-01', '2025-02-22', '09683488M', 'PRADERA (LA)0004', 'ES2412345678901234567890', 'TURISMO', 'CITROEN XSARA PICASSO 40', 'CABALLOS FISCALES', 13.58, 128.95, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2847, 349),
	(148, '2024-01-01', '2025-02-22', '09685674Y', 'PRADERA (LA)0004', 'ES2412345678901234567890', 'TURISMO', 'PEUGEOT 407.0', 'CABALLOS FISCALES', 18.21, 167.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2848, 350),
	(149, '2024-01-01', '2025-02-22', '09685694A', 'MADRID0011', 'ES2412345678901234567890', 'TURISMO', 'RENAULT MEGANE', 'CABALLOS FISCALES', 21.05, 209.4, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2849, 351),
	(150, '2024-01-01', '2025-02-22', '09688492H', 'CAMINO DE SANTIAGO0028', 'ES2412345678901234567890', 'TURISMO', 'PEUGEOT 507.0', 'CABALLOS FISCALES', 26.03, 209.4, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2850, 352),
	(151, '2024-01-01', '2025-02-22', '09701763H', 'ABAJO0003', 'ES2412345678901234567890', 'AUTOBUS', 'VOVLO B3', 'PLAZAS', 15, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2853, 353),
	(152, '2024-01-01', '2025-02-22', '09703447T', 'MONTE (EL)0043', 'ES2412345678901234567890', 'AUTOBUS', 'MERCEDES H2', 'PLAZAS', 35, 199.35, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2854, 354),
	(153, '2024-01-01', '2025-02-22', '09706119G', 'MONTE (EL)0050', 'ES2412345678901234567890', 'AUTOBUS', 'SCANIA J4', 'PLAZAS', 65, 240.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2855, 355),
	(154, '2024-01-01', '2025-02-22', '09707275X', 'MONTE (EL)0050', 'ES2412345678901234567890', 'CAMION', 'VOLVO M1', 'KG DE CARGA', 950, 68, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2857, 356),
	(155, '2024-01-01', '2025-02-22', '09710840X', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'CAMION', 'SCANIA D1', 'KG DE CARGA', 2500, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2858, 357),
	(156, '2024-01-01', '2025-02-22', '09711691X', 'LEON-SANTANDER0027', 'ES2412345678901234567890', 'CAMION', 'MERCEDES X1', 'KG DE CARGA', 6500, 208.45, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2859, 358),
	(157, '2024-01-01', '2025-02-22', '09713006Z', 'REAL0017', 'ES2412345678901234567890', 'CAMION', 'VOLVO R1', 'KG DE CARGA', 12500, 271.85, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2860, 359),
	(158, '2024-01-01', '2025-02-22', '09713007S', 'REAL0017', 'ES2412345678901234567890', 'TRACTOR', 'RAZOL CVH', 'CABALLOS FISCALES', 14, 29.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2861, 360),
	(159, '2024-01-01', '2025-02-22', '09713528F', 'LEON-SANTANDER0027', 'ES2412345678901234567890', 'TRACTOR', 'CASE CVH', 'CABALLOS FISCALES', 20, 46.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2862, 361),
	(160, '2024-01-01', '2025-02-22', '09715064W', 'LEON-SANTANDER0027', 'ES2412345678901234567890', 'TRACTOR', 'CASE CVX', 'CABALLOS FISCALES', 31, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2863, 362),
	(161, '2024-01-01', '2025-02-22', '09715167J', 'REAL0017', 'ES2412345678901234567890', 'REMOLQUE', 'JOHN DEERE G23', 'KG DE CARGA', 850, 29.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2864, 363),
	(162, '2024-01-01', '2025-02-22', '09715890T', 'MILGAS (LAS)0001', 'ES2412345678901234567890', 'REMOLQUE', 'JOHN DEERE H75', 'KG DE CARGA', 1800, 46.9, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2865, 364),
	(163, '2024-01-01', '2025-02-22', '09716184H', 'MONTE (EL)0041', 'ES2412345678901234567890', 'REMOLQUE', 'JOHN DEERE V455', 'KG DE CARGA', 3250, 140.15, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2866, 365),
	(164, '2024-01-01', '2025-02-22', '09720049L', 'MONTE (EL)0017', 'ES2412345678901234567890', 'CICLOMOTOR', 'VESPINO GL', 'CENTIMETROS CUBICOS', 49, 8.65, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2869, 366),
	(165, '2024-01-01', '2025-02-22', '09720049L', 'MONTE (EL)0017', 'ES2412345678901234567890', 'MOTOCICLETA', 'YAMAHA SCO 125', 'CENTIMETROS CUBICOS', 125, 15.14, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2869, 367),
	(166, '2024-01-01', '2025-02-22', '09720969L', 'PEÑA UBIÑA0188', 'ES2412345678901234567890', 'MOTOCICLETA', 'YAMAHA SCO 300', 'CENTIMETROS CUBICOS', 300, 30.3, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2870, 368),
	(167, '2024-01-01', '2025-02-22', '09722535K', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'MOTOCICLETA', 'HONDA CBR 600', 'CENTIMETROS CUBICOS', 600, 58.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2871, 369),
	(168, '2024-01-01', '2025-02-22', '09722591P', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'MOTOCICLETA', 'HONDA CBR 900', 'CENTIMETROS CUBICOS', 900, 58.5, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2872, 370),
	(169, '2024-01-01', '2025-02-22', '09722827Z', 'IGLESIA (LA)0007', 'ES2412345678901234567890', 'MOTOCICLETA', 'YAMAHA SCO 1500', 'CENTIMETROS CUBICOS', 1500, 121.16, 'N', 0, 'prueba@unileon.es', 'BENAVIDES', 2873, 371);

-- Volcando datos para la tabla ivtm.vehiculos: ~48 rows (aproximadamente)
DELETE FROM `vehiculos`;
INSERT INTO `vehiculos` (`idVehiculo`, `tipo`, `marca`, `modelo`, `matricula`, `numeroBastidor`, `caballosFiscales`, `plazas`, `centimetroscubicos`, `kgcarga`, `exencion`, `fechaMatriculacion`, `fechaAlta`, `fechaBaja`, `fechaBajaTemporal`, `idContribuyente`, `idOrdenanza`) VALUES
	(324, 'MOTOCICLETA', 'YAMAHA', 'GR900', '1234ABC', 'WF0FXXWPDF3Y47888', 0, NULL, 900, NULL, 'N', '2020-01-29', '2023-01-29', NULL, NULL, 2872, 623),
	(325, 'TURISMO', 'RENAULT', 'MEGANE', '1235ABC', 'KMJWVH7HP2U456263', 11.64, NULL, NULL, NULL, 'N', '2020-01-01', '2023-01-01', NULL, NULL, 2846, 602),
	(326, 'TURISMO', 'RENAULT', 'ESPACE 2.0', '1237ABC', 'W0L0AHM75A2100307', 13.29, NULL, NULL, NULL, 'N', '2020-01-02', '2023-01-02', NULL, NULL, 2847, 603),
	(327, 'TURISMO', 'RENAULT', 'CAPTUR', '1238ABC', 'YV2J4DEC02B302081', 18, NULL, NULL, NULL, 'N', '2020-01-03', '2023-01-03', NULL, NULL, 2848, 604),
	(328, 'TURISMO', 'OPEL', 'ASTRA', '1239ABC', 'W0L0TGF48Y6106268', 21, NULL, NULL, NULL, 'N', '2020-01-04', '2023-01-04', NULL, NULL, 2849, 605),
	(329, 'TURISMO', 'KIA', 'SORENTO', '1240ABC', 'WBAAM11090CA03927', 25, NULL, NULL, NULL, 'N', '2020-01-05', '2023-01-05', NULL, NULL, 2850, 605),
	(330, 'AUTOBUS', 'VOLVO', 'F320', '1242ABC', 'VF1BA1U0524627776', NULL, 15, NULL, NULL, 'N', '2020-01-08', '2023-01-08', NULL, NULL, 2853, 606),
	(331, 'AUTOBUS', 'MERCEDES', 'X425', '1243ABC', 'VSSZZZ7MZ8V506376', NULL, 35, NULL, NULL, 'N', '2020-01-09', '2023-01-09', NULL, NULL, 2854, 607),
	(332, 'AUTOBUS', 'SCANIA', 'R4787', '1244ABC', 'JN1KESY61U0005997', NULL, 65, NULL, NULL, 'N', '2020-01-10', '2023-01-10', NULL, NULL, 2855, 608),
	(333, 'CAMION', 'SCANIA', 'G567', '1246ABC', 'WVWZZZ3BZ4E288011', NULL, NULL, NULL, 950, 'N', '2020-01-12', '2023-01-12', NULL, NULL, 2857, 609),
	(334, 'CAMION', 'VOLVO', 'X487', '1247ABC', 'VF32AKFWF44528938', NULL, NULL, NULL, 2500, 'N', '2020-01-13', '2023-01-13', NULL, NULL, 2858, 610),
	(335, 'CAMION', 'MERCEDES', 'N364', '1248ABC', 'WAUZZZ8E76A143187', NULL, NULL, NULL, 6500, 'N', '2020-01-14', '2023-01-14', NULL, NULL, 2859, 611),
	(336, 'CAMION', 'SCANIA', 'L758', '1249ABC', 'WAUZZZ8E26A279615', NULL, NULL, NULL, 12500, 'N', '2020-01-15', '2023-01-15', NULL, NULL, 2860, 612),
	(337, 'TRACTOR', 'JOHN DEERE', 'C37', '1251ABC', 'W0L0TGF48X5100122', 14, NULL, NULL, NULL, 'N', '2020-01-17', '2023-01-17', NULL, NULL, 2861, 613),
	(338, 'TRACTOR', 'JOHN DEERE', 'N91', '1252ABC', 'XTA21214031711893', 20, NULL, NULL, NULL, 'N', '2020-01-18', '2023-01-18', NULL, NULL, 2862, 614),
	(339, 'TRACTOR', 'JOHN DEERE', 'G86', '1253ABC', 'VF1DA0B0527099849', 31, NULL, NULL, NULL, 'N', '2020-01-19', '2023-01-19', NULL, NULL, 2863, 615),
	(340, 'REMOLQUE', 'JOHN DEERE', 'F70', '1255ABC', 'WF0AXXWPDA1D05535', NULL, NULL, NULL, 850, 'N', '2020-01-21', '2023-01-21', NULL, NULL, 2864, 616),
	(341, 'REMOLQUE', 'JOHN DEERE', 'F90', '1256ABC', 'WAUZZZ8E64A046303', NULL, NULL, NULL, 1800, 'N', '2020-01-22', '2023-01-22', NULL, NULL, 2865, 617),
	(342, 'REMOLQUE', 'JOHN DEERE', 'F120', '1257ABC', 'VSSZZZ1MZ4R067053', NULL, NULL, NULL, 3250, 'N', '2020-01-23', '2023-01-23', NULL, NULL, 2866, 618),
	(343, 'CICLOMOTOR', 'VESPINO', 'F9', '1259ABC', 'RFVBRUC5241002446', NULL, NULL, 49, NULL, 'N', '2020-01-25', '2023-01-25', NULL, NULL, 2869, 619),
	(344, 'MOTOCICLETA', 'YAMAHA', 'MTN850-A', '1261ABC', 'W0L0AHL4858136249', NULL, NULL, 125, NULL, 'N', '2020-01-26', '2023-01-26', NULL, NULL, 2869, 621),
	(345, 'MOTOCICLETA', 'HONDA', 'SCOOTER300', '1262ABC', 'TMAD331UAEJ001412', NULL, NULL, 300, NULL, 'N', '2020-01-27', '2023-01-27', NULL, NULL, 2870, 622),
	(346, 'MOTOCICLETA', 'HONDA', 'CBR600F', '1263ABC', 'VSSZZZ6JZER120663', NULL, NULL, 600, NULL, 'N', '2020-01-28', '2023-01-28', NULL, NULL, 2871, 623),
	(347, 'MOTOCICLETA', 'YAMAHA', 'X1500', '1264ABC', 'W0L0TGF6915014070', NULL, NULL, 1500, NULL, 'N', '2020-01-30', '2023-01-30', NULL, NULL, 2873, 624),
	(348, 'TURISMO', 'RENAULT', 'CLIO', '1270ABC', 'WAUZZZ4F76N044649', 9.05, NULL, NULL, NULL, 'N', '2020-01-01', '2023-01-01', NULL, NULL, 2846, 602),
	(349, 'TURISMO', 'CITROEN', 'XSARA PICASSO 40', '1271ABC', 'VSSZZZ1PZ8R049713', 13.58, NULL, NULL, NULL, 'N', '2020-01-02', '2023-01-02', NULL, NULL, 2847, 603),
	(350, 'TURISMO', 'PEUGEOT', '407.0', '1272ABC', 'VTHSC1C1A6H345070', 18.21, NULL, NULL, NULL, 'N', '2020-01-03', '2023-01-03', NULL, NULL, 2848, 604),
	(351, 'TURISMO', 'RENAULT', 'MEGANE', '1273ABC', 'VF1SB8M0537878580', 21.05, NULL, NULL, NULL, 'N', '2020-01-04', '2023-01-04', NULL, NULL, 2849, 605),
	(352, 'TURISMO', 'PEUGEOT', '507.0', '1274ABC', 'WVWZZZ1JZ3W451498', 26.03, NULL, NULL, NULL, 'N', '2020-01-05', '2023-01-05', NULL, NULL, 2850, 605),
	(353, 'AUTOBUS', 'VOVLO', 'B3', '1277ABC', 'VSSZZZ6KZ2R038977', NULL, 15, NULL, NULL, 'N', '2020-01-08', '2023-01-08', NULL, NULL, 2853, 606),
	(354, 'AUTOBUS', 'MERCEDES', 'H2', '1278ABC', 'UU15SDAG548391805', NULL, 35, NULL, NULL, 'N', '2020-01-09', '2023-01-09', NULL, NULL, 2854, 607),
	(355, 'AUTOBUS', 'SCANIA', 'J4', '1279ABC', 'WVWZZZ1KZ7B024041', NULL, 65, NULL, NULL, 'N', '2020-01-10', '2023-01-10', NULL, NULL, 2855, 608),
	(356, 'CAMION', 'VOLVO', 'M1', '1281ABC', 'WF0UXXGAJU5M80705', NULL, NULL, NULL, 950, 'N', '2020-01-12', '2023-01-12', NULL, NULL, 2857, 609),
	(357, 'CAMION', 'SCANIA', 'D1', '1282ABC', 'VSSZZZ6LZ8R121527', NULL, NULL, NULL, 2500, 'N', '2020-01-13', '2023-01-13', NULL, NULL, 2858, 610),
	(358, 'CAMION', 'MERCEDES', 'X1', '1283ABC', 'VF7VDWT0005WT0209', NULL, NULL, NULL, 6500, 'N', '2020-01-14', '2023-01-14', NULL, NULL, 2859, 611),
	(359, 'CAMION', 'VOLVO', 'R1', '1284ABC', 'WVWZZZ1JZ3B190985', NULL, NULL, NULL, 12500, 'N', '2020-01-15', '2023-01-15', NULL, NULL, 2860, 612),
	(360, 'TRACTOR', 'RAZOL', 'CVH', '1286ABC', 'VSKJ4EDA6UY585754', 14, NULL, NULL, NULL, 'N', '2020-01-17', '2023-01-17', NULL, NULL, 2861, 613),
	(361, 'TRACTOR', 'CASE', 'CVH', '1287ABC', 'SAJAF52139BJ46823', 20, NULL, NULL, NULL, 'N', '2020-01-18', '2023-01-18', NULL, NULL, 2862, 614),
	(362, 'TRACTOR', 'CASE', 'CVX', '1288ABC', 'VF1JMSE0638014920', 31, NULL, NULL, NULL, 'N', '2020-01-19', '2023-01-19', NULL, NULL, 2863, 615),
	(363, 'REMOLQUE', 'JOHN DEERE', 'G23', '1290ABC', 'VSSZZZ1MZ4R058682', NULL, NULL, NULL, 850, 'N', '2020-01-21', '2023-01-21', NULL, NULL, 2864, 616),
	(364, 'REMOLQUE', 'JOHN DEERE', 'H75', '1291ABC', 'YV1LS4502N2013205', NULL, NULL, NULL, 1800, 'N', '2020-01-22', '2023-01-22', NULL, NULL, 2865, 617),
	(365, 'REMOLQUE', 'JOHN DEERE', 'V455', '1292ABC', 'YS3FF46WX51056758', NULL, NULL, NULL, 3250, 'N', '2020-01-23', '2023-01-23', NULL, NULL, 2866, 618),
	(366, 'CICLOMOTOR', 'VESPINO', 'GL', '1294ABC', 'VSSZZZ1MZ5R074196', NULL, NULL, 49, NULL, 'N', '2020-01-25', '2023-01-25', NULL, NULL, 2869, 619),
	(367, 'MOTOCICLETA', 'YAMAHA', 'SCO 125', '1296ABC', 'VF33CRHYB83885473', NULL, NULL, 125, NULL, 'N', '2020-01-26', '2023-01-26', NULL, NULL, 2869, 621),
	(368, 'MOTOCICLETA', 'YAMAHA', 'SCO 300', '1297ABC', 'VF1FW18M552505376', NULL, NULL, 300, NULL, 'N', '2020-01-27', '2023-01-27', NULL, NULL, 2870, 622),
	(369, 'MOTOCICLETA', 'HONDA', 'CBR 600', '1298ABC', 'VTRCG3030F0947139', NULL, NULL, 600, NULL, 'N', '2020-01-28', '2023-01-28', NULL, NULL, 2871, 623),
	(370, 'MOTOCICLETA', 'HONDA', 'CBR 900', '1299ABC', 'VF7MFWJYB65831272', 0, NULL, 900, NULL, 'N', '2020-01-29', '2023-01-29', NULL, NULL, 2872, 623),
	(371, 'MOTOCICLETA', 'YAMAHA', 'SCO 1500', '1300ABC', 'VF7N0RHYB73531591', NULL, NULL, 1500, NULL, 'N', '2020-01-30', '2023-01-30', NULL, NULL, 2873, 624);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
