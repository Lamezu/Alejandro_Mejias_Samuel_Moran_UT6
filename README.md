# Alejandro_Mejias_Samuel_Moran_UT6
Descripción del Proyecto
Este proyecto es un sistema de combate por turnos para un videojuego que simula batallas entre personajes con diferentes roles y habilidades. Los jugadores pueden elegir entre varios tipos de personajes, cada uno con sus propias habilidades y puntos de salud (HP). El juego permite una jugabilidad estratégica donde los jugadores pueden atacar, defenderse y utilizar habilidades especiales para obtener ventaja en las batallas.

Características Clave
Roles de Personajes: Los jugadores pueden seleccionar entre diferentes tipos de personajes, incluyendo:

Guerrero
Mago
Arquero
Hechicero
Asesino
Reacciones Elementales: El juego incorpora interacciones elementales como:

Fuego
Agua
Hielo
Rayo/Electrico
Viento
Planta
Mecánicas de Combate:

Batallas 1v1: Los jugadores participan en combates uno a uno.
Puntos de Salud (HP): Cada personaje tiene HP predefinido basado en su rol.
Sistema de Maná: Los personajes tienen una barra de maná que se agota al usar habilidades. El maná puede ser restaurado a través de pociones, que pueden tener penalizaciones (por ejemplo, no poder atacar durante un turno).
Interfaz de Usuario:

Muestra el nombre y rol del jugador.
Presenta una barra de vida actualizada para el jugador y el enemigo.
Las opciones de ataque y habilidades se presentan en un formato de tabla 2x2, permitiendo un máximo de un ataque, una defensa y dos habilidades.
Mecánicas de Juego
Sistema por Turnos: Los jugadores toman turnos para atacar o defenderse.

Reacciones Elementales:

Si un personaje utiliza un ataque elemental, se imbuye con ese elemento.
Si el oponente contraataca con un tipo elemental diferente, ocurre una reacción (por ejemplo, agua contra fuego resulta en vapor).
Cada personaje solo puede estar imbuido con un elemento a la vez, y esto dura un turno.
Habilidades:

Cada personaje tiene habilidades únicas que pueden causar diferentes cantidades de daño y consumir diferentes cantidades de maná.
Las habilidades pueden tener múltiples funciones, como sigilo para los asesinos o robo de daño.

.
├── .git/                  # Archivos de control de versiones
├── .gitignore             # Archivos y carpetas a ignorar por Git
├── README.md              # Documentación del proyecto
├── src/                  # Código fuente del proyecto
│   ├── Main.java          # Clase principal que inicia el juego
│   ├── controller/        # Controladores del juego
│   │   ├── Game.java      # Controlador principal del juego
│   │   ├── BattleController.java # Controlador de batallas
│   ├── model/             # Modelos de datos
│   │   ├── characters/    # Clases de personajes
│   │   │   ├── Character.java (abstract)
│   │   │   ├── PhysicalCharacter.java (abstract)
│   │   │   ├── MagicalCharacter.java (abstract)
│   │   │   ├── Warrior.java
│   │   │   ├── Mage.java
│   │   │   ├── Archer.java
│   │   │   ├── Sorcerer.java
│   │   │   └── Assassin.java
│   │   ├── interfaces/    # Interfaces para los personajes
│   │   │   ├── Healable.java
│   │   │   ├── Defendable.java
│   │   │   ├── Magical.java
│   │   │   ├── Movable.java
│   │   │   └── Flying.java
│   │   ├── battle/        # Clases relacionadas con el combate
│   │   │   ├── Battle.java
│   │   │   ├── Element.java (enum)
│   │   │   ├── Reaction.java
│   │   │   └── Potion.java
│   │   └── util/          # Utilidades del proyecto
│   │       └── DamageCalculator.java
│   └── view/              # Clases de la interfaz de usuario
│       ├── BattleInterface.java

Conclusión
Este proyecto tiene como objetivo crear un sistema de combate por turnos atractivo y estratégico que incorpore interacciones elementales y roles de personajes. El juego está diseñado para ser simple pero desafiante, alentando a los jugadores a pensar críticamente sobre sus movimientos y las dinámicas elementales en juego.
