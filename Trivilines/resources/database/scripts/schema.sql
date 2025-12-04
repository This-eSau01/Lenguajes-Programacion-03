-- 1. ESTRUCTURA DE TABLAS
CREATE TABLE IF NOT EXISTS categorias (
                                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                                          nombre TEXT NOT NULL,
                                          descripcion TEXT,
                                          ruta_icono TEXT
);

CREATE TABLE IF NOT EXISTS preguntas (
                                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                                         texto TEXT NOT NULL,
                                         opcion1 TEXT NOT NULL,
                                         opcion2 TEXT NOT NULL,
                                         opcion3 TEXT NOT NULL,
                                         opcion4 TEXT NOT NULL,
                                         indice_respuesta_correcta INTEGER NOT NULL,
                                         categoria_id INTEGER,
                                         nivel_dificultad INTEGER DEFAULT 1, -- 1=Fácil, 2=Medio, 3=Difícil
                                         tiempo_limite_segundos INTEGER DEFAULT 30,
                                         FOREIGN KEY(categoria_id) REFERENCES categorias(id)
    );

CREATE TABLE IF NOT EXISTS jugadores (
                                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                                         nombre TEXT NOT NULL UNIQUE,
                                         fecha_registro TEXT,
                                         partidas_jugadas INTEGER DEFAULT 0,
                                         partidas_ganadas INTEGER DEFAULT 0,
                                         puntaje_maximo INTEGER DEFAULT 0,
                                         puntaje_total_acumulado INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS partidas (
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        jugador_id INTEGER,
                                        fecha_inicio TEXT,
                                        fecha_fin TEXT,
                                        preguntas_respondidas INTEGER DEFAULT 0,
                                        respuestas_correctas INTEGER DEFAULT 0,
                                        respuestas_incorrectas INTEGER DEFAULT 0,
                                        puntaje_total INTEGER DEFAULT 0,
                                        victoria INTEGER DEFAULT 0,
                                        FOREIGN KEY(jugador_id) REFERENCES jugadores(id)
    );

-- 2. CATEGORÍAS
INSERT OR IGNORE INTO categorias (id, nombre, descripcion, ruta_icono) VALUES
(1, 'Historia', 'Eventos del pasado', 'imagenes/icono_historia.png'),
(2, 'Ciencia', 'Mundo natural y tecnología', 'imagenes/icono_ciencia.png'),
(3, 'Deportes', 'Atletas y competiciones', 'imagenes/icono_deportes.png'),
(4, 'Arte', 'Cultura y expresiones', 'imagenes/icono_arte.png');

-- Categoria : Histotia
-- Nivel 1 (Fácil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Quién descubrió América?', 'Colón', 'Magallanes', 'Vespucio', 'Cortés', 0, 1, 1),
                                                                                                                                 ('¿En qué país se encuentran las pirámides de Giza?', 'México', 'Perú', 'Egipto', 'Sudán', 2, 1, 1),
                                                                                                                                 ('¿Quién fue el primer presidente de EE.UU.?', 'Lincoln', 'Washington', 'Jefferson', 'Bush', 1, 1, 1),
                                                                                                                                 ('¿Qué imperio construyó el Coliseo?', 'Griego', 'Romano', 'Otomano', 'Mongol', 1, 1, 1),
                                                                                                                                 ('¿Cuál fue el conflicto de 1939 a 1945?', 'I Guerra Mundial', 'Guerra Fría', 'II Guerra Mundial', 'Guerra de Vietnam', 2, 1, 1),
                                                                                                                                 ('¿Dónde nació Napoleón Bonaparte?', 'Córcega', 'París', 'Elba', 'Lyon', 0, 1, 1),
                                                                                                                                 ('¿Qué muro dividió Berlín?', 'Muro de Hierro', 'Muro de Berlín', 'Muro Rojo', 'Gran Muralla', 1, 1, 1),
                                                                                                                                 ('¿Civilización de Machu Picchu?', 'Mayas', 'Aztecas', 'Incas', 'Mapuches', 2, 1, 1),
                                                                                                                                 ('¿Reina de Egipto famosa por su belleza?', 'Nefertiti', 'Hatshepsut', 'Cleopatra', 'Isis', 2, 1, 1),
                                                                                                                                 ('¿Moneda de la Unión Europea?', 'Dólar', 'Peso', 'Libra', 'Euro', 3, 1, 1);

-- Nivel 2 (Medio)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Año de la Revolución Francesa?', '1776', '1789', '1812', '1492', 1, 1, 2),
                                                                                                                                 ('¿Quién pintó el Guernica?', 'Dalí', 'Miró', 'Picasso', 'Goya', 2, 1, 2),
                                                                                                                                 ('¿En qué año llegó el hombre a la Luna?', '1965', '1969', '1972', '1959', 1, 1, 2),
                                                                                                                                 ('¿Capital del Imperio Azteca?', 'Teotihuacán', 'Tenochtitlán', 'Tulum', 'Cuzco', 1, 1, 2),
                                                                                                                                 ('¿Quién escribió "El Príncipe"?', 'Dante', 'Maquiavelo', 'Bocaccio', 'Petrarca', 1, 1, 2),
                                                                                                                                 ('¿Guerra entre el Norte y Sur de EE.UU.?', 'Independencia', 'Secesión', 'Vietnam', 'Corea', 1, 1, 2),
                                                                                                                                 ('¿Líder de la URSS en la II Guerra Mundial?', 'Lenin', 'Stalin', 'Trotsky', 'Putin', 1, 1, 2),
                                                                                                                                 ('¿Quién descubrió la penicilina?', 'Pasteur', 'Fleming', 'Curie', 'Darwin', 1, 1, 2),
                                                                                                                                 ('¿Ciudad bombardeada con la primera bomba atómica?', 'Nagasaki', 'Tokio', 'Hiroshima', 'Kioto', 2, 1, 2),
                                                                                                                                 ('¿Rey inglés con seis esposas?', 'Enrique VIII', 'Jorge III', 'Ricardo I', 'Carlos II', 0, 1, 2);

-- Nivel 3 (Difícil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Batalla final de Napoleón?', 'Austerlitz', 'Waterloo', 'Trafalgar', 'Leipzig', 1, 1, 3),
                                                                                                                                 ('¿Año de la caída de Constantinopla?', '1453', '1492', '1517', '1099', 0, 1, 3),
                                                                                                                                 ('¿Primer emperador romano?', 'Julio César', 'Augusto', 'Nerón', 'Calígula', 1, 1, 3),
                                                                                                                                 ('¿Tratado que puso fin a la I Guerra Mundial?', 'Tordesillas', 'Versalles', 'Viena', 'Utrecht', 1, 1, 3),
                                                                                                                                 ('¿Dios egipcio de los muertos?', 'Ra', 'Horus', 'Anubis', 'Osiris', 3, 1, 3),
                                                                                                                                 ('¿Quién descifró la Piedra de Rosetta?', 'Champollion', 'Carter', 'Evans', 'Schliemann', 0, 1, 3),
                                                                                                                                 ('¿Dinastía china que construyó la Gran Muralla?', 'Han', 'Ming', 'Qin', 'Tang', 2, 1, 3),
                                                                                                                                 ('¿Nombre del barco de Charles Darwin?', 'Endeavour', 'Beagle', 'Victoria', 'Mayflower', 1, 1, 3),
                                                                                                                                 ('¿Año de la independencia de México?', '1810', '1821', '1910', '1857', 0, 1, 3),
                                                                                                                                 ('¿Zar ejecutado en la Revolución Rusa?', 'Nicolás I', 'Alejandro II', 'Nicolás II', 'Pedro I', 2, 1, 3);



-- CATEGOria : Ciencia
-- Nivel 1 (Fácil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Símbolo químico del agua?', 'HO', 'H2O', 'Ag', 'Au', 1, 2, 1),
                                                                                                                                 ('¿Planeta más cercano al Sol?', 'Venus', 'Marte', 'Mercurio', 'Tierra', 2, 2, 1),
                                                                                                                                 ('¿Animal más rápido?', 'León', 'Guepardo', 'Águila', 'Caballo', 1, 2, 1),
                                                                                                                                 ('¿Qué respiramos?', 'Helio', 'Oxígeno', 'Carbono', 'Metano', 1, 2, 1),
                                                                                                                                 ('¿Centro del sistema solar?', 'Tierra', 'Luna', 'Sol', 'Marte', 2, 2, 1),
                                                                                                                                 ('¿Cuántas patas tiene una araña?', '4', '6', '8', '10', 2, 2, 1),
                                                                                                                                 ('¿Estado del agua en el hielo?', 'Líquido', 'Gaseoso', 'Sólido', 'Plasma', 2, 2, 1),
                                                                                                                                 ('¿Órgano que bombea sangre?', 'Pulmón', 'Cerebro', 'Hígado', 'Corazón', 3, 2, 1),
                                                                                                                                 ('¿Color de la clorofila?', 'Rojo', 'Amarillo', 'Verde', 'Azul', 2, 2, 1),
                                                                                                                                 ('¿Fuerza que nos mantiene en el suelo?', 'Magnetismo', 'Gravedad', 'Fricción', 'Inercia', 1, 2, 1);

-- Nivel 2 (Medio)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Símbolo químico del Oro?', 'Or', 'Go', 'Au', 'Ag', 2, 2, 2),
                                                                                                                                 ('¿Huesos en el cuerpo adulto?', '200', '206', '215', '250', 1, 2, 2),
                                                                                                                                 ('¿Gas más abundante en la atmósfera?', 'Oxígeno', 'Nitrógeno', 'Hidrógeno', 'CO2', 1, 2, 2),
                                                                                                                                 ('¿Planeta con anillos visibles?', 'Júpiter', 'Saturno', 'Urano', 'Neptuno', 1, 2, 2),
                                                                                                                                 ('¿Quién propuso la evolución?', 'Newton', 'Einstein', 'Darwin', 'Galileo', 2, 2, 2),
                                                                                                                                 ('¿Metal líquido a temp. ambiente?', 'Plomo', 'Mercurio', 'Hierro', 'Aluminio', 1, 2, 2),
                                                                                                                                 ('¿Parte de la célula con ADN?', 'Citoplasma', 'Núcleo', 'Membrana', 'Ribosoma', 1, 2, 2),
                                                                                                                                 ('¿Velocidad de la luz (km/s)?', '100,000', '300,000', '500,000', '1,000,000', 1, 2, 2),
                                                                                                                                 ('¿Ciencia que estudia los fósiles?', 'Biología', 'Paleontología', 'Geología', 'Arqueología', 1, 2, 2),
                                                                                                                                 ('¿Cuántos dientes tiene un adulto?', '28', '30', '32', '34', 2, 2, 2);

-- Nivel 3 (Difícil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Partícula subatómica sin carga?', 'Protón', 'Electrón', 'Neutrón', 'Quark', 2, 2, 3),
                                                                                                                                 ('¿Estrella más cercana tras el Sol?', 'Sirio', 'Próxima Centauri', 'Vega', 'Betelgeuse', 1, 2, 3),
                                                                                                                                 ('¿PH neutro?', '0', '7', '14', '5', 1, 2, 3),
                                                                                                                                 ('¿Elemento más ligero?', 'Helio', 'Oxígeno', 'Litio', 'Hidrógeno', 3, 2, 3),
                                                                                                                                 ('¿Padre de la genética?', 'Mendel', 'Darwin', 'Pasteur', 'Watson', 0, 2, 3),
                                                                                                                                 ('¿Hueso más largo del cuerpo?', 'Húmero', 'Tibia', 'Fémur', 'Peroné', 2, 2, 3),
                                                                                                                                 ('¿Teoría del origen del universo?', 'Big Crunch', 'Big Bang', 'Estado Estacionario', 'Inflación', 1, 2, 3),
                                                                                                                                 ('¿Capa más externa de la Tierra?', 'Manto', 'Núcleo', 'Corteza', 'Atmósfera', 2, 2, 3),
                                                                                                                                 ('¿Unidad de fuerza en el SI?', 'Joule', 'Watt', 'Newton', 'Pascal', 2, 2, 3),
                                                                                                                                 ('¿Inventor de la corriente alterna?', 'Edison', 'Tesla', 'Volta', 'Ampere', 1, 2, 3);



-- Categoaria: DEPORTES
-- Nivel 1 (Fácil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Jugadores en cancha (fútbol)?', '9', '10', '11', '12', 2, 3, 1),
                                                                                                                                 ('¿Deporte de Michael Jordan?', 'Fútbol', 'Béisbol', 'Baloncesto', 'Golf', 2, 3, 1),
                                                                                                                                 ('¿Duración partido de fútbol?', '45 min', '60 min', '90 min', '100 min', 2, 3, 1),
                                                                                                                                 ('¿Objeto usado en tenis?', 'Bate', 'Raqueta', 'Palo', 'Guante', 1, 3, 1),
                                                                                                                                 ('¿País del "Jogo Bonito"?', 'Argentina', 'Brasil', 'Alemania', 'España', 1, 3, 1),
                                                                                                                                 ('¿Dónde se encesta en baloncesto?', 'Arco', 'Red', 'Canasta', 'Portería', 2, 3, 1),
                                                                                                                                 ('¿Color tarjeta de expulsión?', 'Amarilla', 'Verde', 'Roja', 'Azul', 2, 3, 1),
                                                                                                                                 ('¿Rey del fútbol?', 'Pelé', 'Maradona', 'Messi', 'Ronaldo', 0, 3, 1),
                                                                                                                                 ('¿Deporte de Rafael Nadal?', 'Golf', 'Tenis', 'Pádel', 'Squash', 1, 3, 1),
                                                                                                                                 ('¿Evento deportivo cada 4 años?', 'Mundial', 'Super Bowl', 'Olimpiadas', 'Champions', 2, 3, 1);

-- Nivel 2 (Medio)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Origen de los Juegos Olímpicos?', 'Roma', 'Atenas', 'Esparta', 'Olimpia', 3, 3, 2),
                                                                                                                                 ('¿Sets para ganar en Grand Slam (H)?', '2', '3', '4', '5', 1, 3, 2),
                                                                                                                                 ('¿País con más mundiales?', 'Alemania', 'Italia', 'Brasil', 'Argentina', 2, 3, 2),
                                                                                                                                 ('¿Deporte del Super Bowl?', 'Béisbol', 'Rugby', 'Fútbol Americano', 'Hockey', 2, 3, 2),
                                                                                                                                 ('¿Distancia maratón?', '40 km', '42.195 km', '50 km', '21 km', 1, 3, 2),
                                                                                                                                 ('¿Máximo goleador histórico (aprox)?', 'Messi', 'Pelé', 'C. Ronaldo', 'Romario', 2, 3, 2),
                                                                                                                                 ('¿Estilo de natación más rápido?', 'Braza', 'Espalda', 'Mariposa', 'Crol', 3, 3, 2),
                                                                                                                                 ('¿Cuándo se celebró el primer mundial?', '1920', '1930', '1950', '1940', 1, 3, 2),
                                                                                                                                 ('¿Anillos olímpicos?', '4', '5', '6', '7', 1, 3, 2),
                                                                                                                                 ('¿Deporte de Tiger Woods?', 'Tenis', 'Golf', 'Póker', 'Billar', 1, 3, 2);

-- Nivel 3 (Difícil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Sede JJOO 1992?', 'Seúl', 'Barcelona', 'Atlanta', 'Sídney', 1, 3, 3),
                                                                                                                                 ('¿Único Grand Slam en arcilla?', 'Wimbledon', 'US Open', 'Roland Garros', 'Aus Open', 2, 3, 3),
                                                                                                                                 ('¿Quién ganó el mundial 2010?', 'Holanda', 'Brasil', 'España', 'Alemania', 2, 3, 3),
                                                                                                                                 ('¿Puntos de un Touchdown?', '3', '5', '6', '7', 2, 3, 3),
                                                                                                                                 ('¿Altura de la canasta (NBA)?', '2.90m', '3.05m', '3.15m', '3.00m', 1, 3, 3),
                                                                                                                                 ('¿Récord 100m lisos?', '9.58s', '9.69s', '9.72s', '9.63s', 0, 3, 3),
                                                                                                                                 ('¿Equipo con más Champions?', 'Milán', 'Liverpool', 'Real Madrid', 'Bayern', 2, 3, 3),
                                                                                                                                 ('¿País inventor del voleibol?', 'Brasil', 'Rusia', 'EE.UU.', 'Francia', 2, 3, 3),
                                                                                                                                 ('¿Duración asalto boxeo profesional?', '2 min', '3 min', '4 min', '5 min', 1, 3, 3),
                                                                                                                                 ('¿Peso de un balón de fútbol (FIFA)?', '350g', '410-450g', '500g', '600g', 1, 3, 3);


--Categoria Arte
-- Nivel 1 (Fácil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Pintor de la Mona Lisa?', 'Van Gogh', 'Da Vinci', 'Picasso', 'Dalí', 1, 4, 1),
                                                                                                                                 ('¿Séptimo arte?', 'Música', 'Cine', 'Danza', 'Teatro', 1, 4, 1),
                                                                                                                                 ('¿Instrumento de Beethoven?', 'Violín', 'Piano', 'Flauta', 'Trompeta', 1, 4, 1),
                                                                                                                                 ('¿Autor de Don Quijote?', 'Lope', 'Cervantes', 'Góngora', 'Quevedo', 1, 4, 1),
                                                                                                                                 ('¿Dónde está el Louvre?', 'Londres', 'París', 'Roma', 'Berlín', 1, 4, 1),
                                                                                                                                 ('¿Quién escribió Romeo y Julieta?', 'Shakespeare', 'Hemingway', 'Dickens', 'Twain', 0, 4, 1),
                                                                                                                                 ('¿Estatua sin brazos famosa?', 'David', 'Venus de Milo', 'Pensador', 'Piedad', 1, 4, 1),
                                                                                                                                 ('¿Pintor de girasoles?', 'Monet', 'Van Gogh', 'Renoir', 'Degas', 1, 4, 1),
                                                                                                                                 ('¿Arte de plegar papel?', 'Ikebana', 'Origami', 'Kirigami', 'Sushi', 1, 4, 1),
                                                                                                                                 ('¿Rey del Pop?', 'Elvis', 'Prince', 'Michael Jackson', 'Bowie', 2, 4, 1);

-- Nivel 2 (Medio)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Autor de Cien años de soledad?', 'Borges', 'Cortázar', 'García Márquez', 'Neruda', 2, 4, 2),
                                                                                                                                 ('¿Estilo de Notre Dame?', 'Románico', 'Gótico', 'Barroco', 'Neoclásico', 1, 4, 2),
                                                                                                                                 ('¿Pintor del surrealismo?', 'Picasso', 'Dalí', 'Matisse', 'Warhol', 1, 4, 2),
                                                                                                                                 ('¿Quién compuso las 4 estaciones?', 'Bach', 'Vivaldi', 'Mozart', 'Verdi', 1, 4, 2),
                                                                                                                                 ('¿Escultor del David?', 'Donatello', 'Miguel Ángel', 'Bernini', 'Rodin', 1, 4, 2),
                                                                                                                                 ('¿Dónde está la Capilla Sixtina?', 'Florencia', 'Venecia', 'Vaticano', 'Milán', 2, 4, 2),
                                                                                                                                 ('¿Pintor de La Última Cena?', 'Rafael', 'Da Vinci', 'Caravaggio', 'Tiziano', 1, 4, 2),
                                                                                                                                 ('¿Museo del Prado ubicación?', 'Barcelona', 'Sevilla', 'Madrid', 'Bilbao', 2, 4, 2),
                                                                                                                                 ('¿Autor de El Principito?', 'Verne', 'Saint-Exupéry', 'Hugo', 'Dumas', 1, 4, 2),
                                                                                                                                 ('¿Pintor español cubista?', 'Velázquez', 'Goya', 'Picasso', 'Murillo', 2, 4, 2);

-- Nivel 3 (Difícil)
INSERT INTO preguntas (texto, opcion1, opcion2, opcion3, opcion4, indice_respuesta_correcta, categoria_id, nivel_dificultad) VALUES
                                                                                                                                 ('¿Arquitecto de la Sagrada Familia?', 'Gaudí', 'Calatrava', 'Moneo', 'Foster', 0, 4, 3),
                                                                                                                                 ('¿Quién pintó Las Meninas?', 'Zurbarán', 'Velázquez', 'Ribera', 'El Greco', 1, 4, 3),
                                                                                                                                 ('¿Autor de la Divina Comedia?', 'Petrarca', 'Dante', 'Bocaccio', 'Maquiavelo', 1, 4, 3),
                                                                                                                                 ('¿Movimiento de Monet?', 'Cubismo', 'Realismo', 'Impresionismo', 'Fauvismo', 2, 4, 3),
                                                                                                                                 ('¿Quién compuso la Flauta Mágica?', 'Beethoven', 'Wagner', 'Mozart', 'Chopin', 2, 4, 3),
                                                                                                                                 ('¿Autor de Los Miserables?', 'Zola', 'Flaubert', 'Victor Hugo', 'Balzac', 2, 4, 3),
                                                                                                                                 ('¿Pintor de La persistencia de la memoria?', 'Magritte', 'Dalí', 'Ernst', 'Miró', 1, 4, 3),
                                                                                                                                 ('¿Quién escribió La Odisea?', 'Sófocles', 'Homero', 'Virgilio', 'Platón', 1, 4, 3),
                                                                                                                                 ('¿País del Renacimiento?', 'Francia', 'España', 'Italia', 'Grecia', 2, 4, 3),
                                                                                                                                 ('¿Año nacimiento del cine (Lumière)?', '1880', '1895', '1905', '1910', 1, 4, 3);