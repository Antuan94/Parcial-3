package control;

import modelo.EntidadVideojuego;
import java.util.ArrayList;
import java.util.List;

public class MotorJuego {
    public enum EstadoJuego { MENU, JUGANDO, PAUSA, GAME_OVER }
    
    private EstadoJuego estadoActual;
    private List<EntidadVideojuego> entidades;

    public MotorJuego() {
        this.estadoActual = EstadoJuego.MENU;
        this.entidades = new ArrayList<>();
    }

    public void iniciarPartida() {
        this.estadoActual = EstadoJuego.JUGANDO;
        System.out.println("[SISTEMA] Partida Iniciada.");
    }

    public void agregarEntidad(EntidadVideojuego entidad) {
        this.entidades.add(entidad);
        System.out.println("[SISTEMA] Entidad añadida: " + entidad.getNombre());
    }

    public void eliminarEntidad(EntidadVideojuego entidad) {
        this.entidades.remove(entidad);
        System.out.println("[SISTEMA] Entidad eliminada: " + entidad.getNombre());
    }

    public void actualizar() {
    if (estadoActual != EstadoJuego.JUGANDO) {
        System.out.println("[SISTEMA] El motor está en reposo.");
        return;
    }

    System.out.println("--- INICIO TICK DEL BUCLE ---");
    // Hacemos una copia para evitar errores si eliminamos elementos al colisionar
    java.util.List<modelo.EntidadVideojuego> copia = new java.util.ArrayList<>(entidades);
    for (modelo.EntidadVideojuego entidad : copia) {
        // Le pasamos el jugador a la actualización para que los enemigos sepan dónde está
        modelo.Jugador jugadorPrincipal = null;
        for (modelo.EntidadVideojuego e : entidades) {
            if (e instanceof modelo.Jugador) { jugadorPrincipal = (modelo.Jugador) e; break; }
        }
        
        // Modificación del comportamiento del enemigo según la distancia
        if (entidad instanceof modelo.Enemigo && jugadorPrincipal != null) {
            double distancia = Math.sqrt(Math.pow(entidad.getX() - jugadorPrincipal.getX(), 2) + 
                                         Math.pow(entidad.getY() - jugadorPrincipal.getY(), 2));
            modelo.Enemigo enemigo = (modelo.Enemigo) entidad;
            
            if (distancia < 25.0) {
                enemigo.setComportamientoActual("ATACAR");
            } else if (distancia <= 70.0) {
                enemigo.setComportamientoActual("PERSEGUIR");
                // El enemigo avanza lentamente hacia el jugador
                if (enemigo.getX() < jugadorPrincipal.getX()) enemigo.setX(enemigo.getX() + 2);
                else enemigo.setX(enemigo.getX() - 2);
            } else {
                enemigo.setComportamientoActual("PATRULLAR");
            }
        }
        entidad.actualizar();
    }
    
    // Llamamos a nuestro detector avanzado
    verificarColisiones();
    System.out.println("--- FIN TICK DEL BUCLE ---\n");
}

        public void verificarColisiones() {
    // Buscamos al jugador en la lista de entidades
    modelo.Jugador jugador = null;
    for (modelo.EntidadVideojuego e : entidades) {
        if (e instanceof modelo.Jugador) {
            jugador = (modelo.Jugador) e;
            break;
        }
    }
    if (jugador == null) return;

    java.util.List<modelo.EntidadVideojuego> aEliminar = new java.util.ArrayList<>();

    // Comparamos al jugador con el resto de entidades
    for (modelo.EntidadVideojuego entidad : entidades) {
        if (entidad == jugador) continue;

        // Algoritmo matemático AABB (Cajas delimitadoras)
        boolean colisionX = jugador.getX() < entidad.getX() + entidad.getW() &&
                            jugador.getX() + jugador.getW() > entidad.getX();
        boolean colisionY = jugador.getY() < entidad.getY() + entidad.getH() &&
                            jugador.getY() + jugador.getH() > entidad.getY();

        if (colisionX && colisionY) {
            System.out.println("[COLISIÓN] Chocaste con: " + entidad.getNombre());
            
            if (entidad instanceof modelo.Enemigo) {
                jugador.setVida(jugador.getVida() - 20);
                System.out.println("[DAÑO] El enemigo te quita 20 de vida. Vida restante: " + jugador.getVida());
            } else if (entidad.getNombre().startsWith("Moneda")) {
                jugador.sumarPuntos(10);
                System.out.println("[RECOLECCIÓN] ¡Moneda atrapada! +10 puntos.");
                aEliminar.add(entidad);
            }
        }
    }

    // Limpiamos las monedas recolectadas
    for (modelo.EntidadVideojuego e : aEliminar) {
        eliminarEntidad(e);
    }
}

        System.out.println("--- INICIO TICK DEL BUCLE ---");
        for (EntidadVideojuego entidad : entidades) {
            entidad.actualizar();
        }
        System.out.println("--- FIN TICK DEL BUCLE ---\n");
    }

    public EstadoJuego getEstadoActual() { return estadoActual; }
    public List<EntidadVideojuego> getEntidades() { return entidades; }
}