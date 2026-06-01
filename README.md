# Motor de Videojuegos 2D - Pixel Quest Engine

## 1. Título y Temática Elegida
**Pixel Quest** es un simulador de lógica interna para un motor de videojuego 2D enfocado en el género **RPG de Supervivencia Medieval en Mundo Abierto**. El sistema controla de forma interna el ciclo de vida de los personajes, el procesamiento matemático de colisiones en dos dimensiones mediante el algoritmo AABB y el comportamiento dinámico de la IA de los enemigos por proximidad euclidiana, todo ello visualizado mediante trazas log por consola.

---

## 2. Arquitectura del Software
La arquitectura se rige bajo un modelo minimalista orientado a objetos, limitando estrictamente el acoplamiento a las clases permitidas por el diseño:

* **`Main`**: Conductor del sistema. Emula las interacciones del jugador mediante comandos de teclado y despliega el bucle de juego secuencial por consola.
* **`MotorJuego`**: El cerebro del motor. Controla la máquina de estados finita del juego (`MENU`, `JUGANDO`, `PAUSA`, `GAME_OVER`), gestiona la colección de entidades dinámicas y delega el detector matemático de colisiones.
* **`EntidadVideojuego`**: Clase abstracta base que unifica las propiedades espaciales cartesianas ($x, y, w, h$), la gestión del estado vital y la referencia del sprite.
* **`Jugador`**: Especialización de entidad controlable con persistencia de estadísticas propias de la partida (puntuación acumulada).
* **`Enemigo`**: NPC dinámico dotado de una máquina de estados para su IA reactiva que muta automáticamente según su cercanía espacial al jugador.

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

    EntidadVideojuego <|-- Jugador
    EntidadVideojuego <|-- Enemigo
    MotorJuego --> EntidadVideojuego : Contiene


    graph LR
    Jugador((Actor: Jugador))
    CU01[CU-01: Iniciar Partida]
    CU02[CU-02: Mover Entidad]
    
    Jugador --> CU01
    Jugador --> CU02


    ## 5. Especificación de Casos de Uso

| Campo | Descripción |
| :--- | :--- |
| **Nombre** | CU-01 Iniciar Partida |
| **Objetivo** | Cambiar el estado del motor a juego activo y habilitar el ciclo del bucle principal. |
| **Actor Principal** | Jugador. |
| **Precondiciones** | El motor debe estar en estado `MENU`. |
| **Flujo Principal** | 1. El usuario solicita iniciar la partida de simulación.<br>2. El sistema cambia el estado a `JUGANDO`. |
| **Flujos Alternativos** | Si el motor ya está en modo `JUGANDO`, el sistema emite una advertencia de error y aborta el proceso. |
| **Postcondiciones** | El motor queda listo para procesar los ticks cíclicos del bucle de juego. |
| **Reglas de Negocio** | No se puede reiniciar una partida en curso sin pasar por una limpieza de estado previa. |

---

| Campo | Descripción |
| :--- | :--- |
| **Nombre** | CU-02 Mover Entidad |
| **Objetivo** | Alterar las coordenadas del jugador en el mapa cartesiano mediante comandos simulados de dirección. |
| **Actor Principal** | Jugador. |
| **Precondiciones** | El estado de la simulación del motor debe ser estrictamente `JUGANDO`. |
| **Flujo Principal** | 1. Se recibe el comando de dirección (`ARRIBA`, `ABAJO`, `IZQUIERDA`, `DERECHA`).<br>2. Se modifican los valores posicionales de los ejes X o Y de la entidad.<br>3. Se evalúan colisiones en el siguiente ciclo del bucle. |
| **Flujos Alternativos** | Si el motor se encuentra en estado de `PAUSA`, el comando de movimiento se congela y se descarta. |
| **Postcondiciones** | Las coordenadas del jugador varían en memoria de forma inmediata. |
| **Reglas de Negocio** | Cada comando desplaza a la entidad exactamente un delta de traslación de 5 unidades. |

## 6. Bitácora del Uso de IA

### Herramienta utilizada y rol
Se utilizó **Gemini** configurado bajo el rol de *Asistente Experto en Arquitectura de Videojuegos y Gestión de Repositorios con Git-Flow*.

### Muestra de Prompts
1. *"Escribe una estructura abstracta EntidadVideojuego con coordenadas espaciales x, y, w, h en Java y una subclase Jugador que herede de ella."*
2. *"Genera un algoritmo matemático puro AABB para detectar si dos entidades rectangulares se están solapando usando operadores condicionales lógicos."*

### Control de Errores de la IA
La IA inicialmente intentó importar la librería gráfica `java.awt.Rectangle` para resolver las colisiones mediante el método estructurado `.intersects()`. Se le corrigió manualmente exigiéndole que utilizara únicamente lógica matemática nativa con operadores condicionales sobre variables primitivas enteras, cumpliendo así la restricción estricta de evitar cualquier interfaz o librería gráfica externa dentro del motor lógico.

### Reflexión Crítica
El uso de asistentes de IA permite agilizar enormemente la generación de código base redundante (*boilerplate*) y optimiza el formateo de documentación compleja como tablas Markdown y código sintáctico de Mermaid. Sin embargo, bajo presión de tiempo existe el peligro de aceptar código con dependencias innecesarias o sobre-ingeniería que rompa las restricciones impuestas de las guías de entrega. El criterio técnico del programador es vital para filtrar las soluciones.