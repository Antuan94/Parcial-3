# Motor de Videojuegos 2D - Pixel Quest Engine

## 1. Título y Temática Elegida
**Pixel Quest** es un simulador de lógica interna para un motor de videojuego 2D enfocado en el género **RPG de Supervivencia Medieval**. El sistema controla el ciclo de vida de los personajes, el procesamiento matemático de colisiones en dos dimensiones y el comportamiento de la IA de los enemigos por proximidad.

---

## 2. Arquitectura del Software
La arquitectura se rige bajo un modelo minimalista orientado a objetos, limitando el acoplamiento a las clases permitidas:

* **`Main`**: Conductor del sistema. Emula las interacciones del jugador y despliega el bucle de juego por consola.
* **`MotorJuego`**: El cerebro del motor. Controla la máquina de estados finita (`MENU`, `JUGANDO`, `PAUSA`) y gestiona las colisiones de las entidades.
* **`EntidadVideojuego`**: Clase abstracta base que unifica las propiedades espaciales ($x, y, w, h$) y el estado vital.
* **`Jugador`**: Especialización de entidad controlable que almacena estadísticas de la partida (puntuación).
* **`Enemigo`**: NPC dotado de una IA reactiva que cambia de estado (`PATRULLAR`, `PERSEGUIR`, `ATACAR`) según su cercanía al jugador.

---

## 3. Diagrama de Clases UML
```mermaid
classDiagram
    class EntidadVideojuego {
        <<abstract>>
        #int x
        #int y
        #int w
        #int h
        #String nombre
        #int vida
        +actualizar()* void
    }

    class Jugador {
        -int puntuacion
        +actualizar() void
        +mover(String direccion) void
        +sumarPuntos(int puntos) void
        +getPuntuacion() int
    }

    class Enemigo {
        -String comportamientoActual
        +actualizar() void
        +getComportamientoActual() String
        +setComportamientoActual(String c) void
    }

    class MotorJuego {
        +EstadoJuego estadoActual
        -List~EntidadVideojuego~ entidades
        +iniciarPartida() void
        +actualizar() void
        +verificarColisiones() void
    }

    EntidadVideojuego <|-- Jugador
    EntidadVideojuego <|-- Enemigo
    MotorJuego --> EntidadVideojuego : Contiene