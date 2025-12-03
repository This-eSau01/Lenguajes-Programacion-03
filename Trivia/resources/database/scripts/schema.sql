PRAGMA foreign_keys = ON;

-- Tabla de categorías de preguntas
CREATE TABLE IF NOT EXISTS categorias (
                                          id                INTEGER PRIMARY KEY AUTOINCREMENT,
                                          nombre            TEXT NOT NULL UNIQUE,
                                          descripcion       TEXT,
                                          ruta_icono        TEXT
);

-- Tabla de preguntas
CREATE TABLE IF NOT EXISTS preguntas (
                                         id                        INTEGER PRIMARY KEY AUTOINCREMENT,
                                         texto                     TEXT NOT NULL,
                                         opcion1                   TEXT NOT NULL,
                                         opcion2                   TEXT NOT NULL,
                                         opcion3                   TEXT NOT NULL,
                                         opcion4                   TEXT NOT NULL,
                                         indice_respuesta_correcta INTEGER NOT NULL, -- 0 a 3
                                         categoria_id              INTEGER NOT NULL,
                                         nivel_dificultad          INTEGER NOT NULL DEFAULT 1, -- 1=fácil,2=medio,3=difícil
                                         tiempo_limite_segundos    INTEGER DEFAULT 0,          -- 0 = sin límite
                                         FOREIGN KEY (categoria_id) REFERENCES categorias (id) ON DELETE CASCADE
    );

-- Tabla de jugadores
CREATE TABLE IF NOT EXISTS jugadores (
                                         id                      INTEGER PRIMARY KEY AUTOINCREMENT,
                                         nombre                  TEXT NOT NULL UNIQUE,
                                         fecha_registro          TEXT,   -- ISO-8601 (LocalDateTime en String)
                                         partidas_jugadas        INTEGER NOT NULL DEFAULT 0,
                                         partidas_ganadas        INTEGER NOT NULL DEFAULT 0,
                                         puntaje_maximo          INTEGER NOT NULL DEFAULT 0,
                                         puntaje_total_acumulado INTEGER NOT NULL DEFAULT 0
);

-- Tabla de partidas (para registrar historial y estadísticas)
CREATE TABLE IF NOT EXISTS partidas (
                                        id                    INTEGER PRIMARY KEY AUTOINCREMENT,
                                        jugador_id            INTEGER NOT NULL,
                                        fecha_inicio          TEXT,  -- ISO-8601
                                        fecha_fin             TEXT,  -- ISO-8601
                                        preguntas_respondidas INTEGER NOT NULL DEFAULT 0,
                                        respuestas_correctas  INTEGER NOT NULL DEFAULT 0,
                                        respuestas_incorrectas INTEGER NOT NULL DEFAULT 0,
                                        puntaje_total         INTEGER NOT NULL DEFAULT 0,
                                        victoria              INTEGER NOT NULL DEFAULT 0, -- 0 = false, 1 = true
                                        FOREIGN KEY (jugador_id) REFERENCES jugadores (id) ON DELETE CASCADE
    );

-- Tabla de comodines usados o asignados (opcional, para persistir su estado)
CREATE TABLE IF NOT EXISTS comodines (
                                         id              INTEGER PRIMARY KEY AUTOINCREMENT,
                                         jugador_id      INTEGER,
                                         partida_id      INTEGER,
                                         tipo            TEXT NOT NULL,  -- valor de TipoComodin (ej: "CINCUENTA_CINCUENTA")
                                         disponible      INTEGER NOT NULL DEFAULT 1,
                                         usos_restantes  INTEGER NOT NULL DEFAULT 1,
                                         FOREIGN KEY (jugador_id) REFERENCES jugadores (id) ON DELETE CASCADE,
    FOREIGN KEY (partida_id) REFERENCES partidas (id) ON DELETE CASCADE
    );


INSERT OR IGNORE INTO categorias (id, nombre, descripcion, ruta_icono) VALUES
    (1, 'Ciencia',         'Preguntas relacionadas con ciencias naturales y tecnología', 'imagenes/categorias/ciencia.png'),
    (2, 'Arte',            'Pintura, música, literatura y otras disciplinas artísticas', 'imagenes/categorias/arte.png'),
    (3, 'Deportes',        'Deportes individuales y en equipo, reglas y eventos',       'imagenes/categorias/deportes.png'),
    (4, 'Historia',        'Hechos históricos, personajes y acontecimientos importantes', 'imagenes/categorias/historia.png'),
    (5, 'Geografía',       'Países, capitales, relieve, ríos y montañas',              'imagenes/categorias/geografia.png'),
    (6, 'Entretenimiento', 'Cine, series, videojuegos, cultura popular',               'imagenes/categorias/entretenimiento.png');


INSERT OR IGNORE INTO preguntas (
    id, texto, opcion1, opcion2, opcion3, opcion4,
    indice_respuesta_correcta, categoria_id, nivel_dificultad, tiempo_limite_segundos
) VALUES
    (1,
     '¿Cuál es el planeta más cercano al Sol?',
     'Venus', 'Mercurio', 'Marte', 'Tierra',
     1, 1, 1, 20),
    (2,
     '¿Quién pintó "La noche estrellada"?',
     'Vincent van Gogh', 'Pablo Picasso', 'Leonardo da Vinci', 'Claude Monet',
     0, 2, 1, 20),
    (3,
     '¿Cuántos jugadores hay en un equipo de fútbol en el campo?',
     '9', '10', '11', '12',
     2, 3, 1, 20),
    (4,
     '¿En qué año comenzó la Segunda Guerra Mundial?',
     '1935', '1937', '1939', '1941',
     2, 4, 2, 25),
    (5,
     '¿Cuál es la capital de Perú?',
     'Quito', 'Lima', 'Bogotá', 'La Paz',
     1, 5, 1, 15),
    (6,
     '¿Cuál de estas películas pertenece al universo cinematográfico de Marvel?',
     'El Padrino', 'Avengers: Endgame', 'Titanic', 'El Señor de los Anillos',
     1, 6, 1, 20);
