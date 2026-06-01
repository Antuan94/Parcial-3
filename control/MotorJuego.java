package control;

import modelo.EntidadVideojuego;
import modelo.Jugador;
import modelo.Enemigo;
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
        System.out.println("[SISTEMA] Motor cambiado a estado JUGANDO.");
    }

    public void agregarEntidad(EntidadVideojuego entidad) {
        this.entidades.add(entidad);
        System.out.println("[SISTEMA] Entidad registrada: " + entidad.getNombre());
    }

    public void eliminarEntidad(EntidadVideojuego entidad) {
        this.entidades.remove(entidad);
        System.out.println("[SISTEMA] Entidad removida: " + entidad.getNombre());
    }

    public void verificarColisiones() {
        Jugador jugador = null;
        for (EntidadVideojuego e : entidades) {
            if (e instanceof Jugador) { jugador = (Jugador) e; break; }
        }
        if (jugador == null) return;

        List<EntidadVideojuego> aEliminar = new ArrayList<>();

        for (EntidadVideojuego entidad : entidades) {
            if (entidad == jugador) continue;

            boolean colisionX = jugador.getX() < entidad.getX() + entidad.getW() &&
                                jugador.getX() + jugador.getW() > entidad.getX();
            boolean colisionY = jugador.getY() < entidad.getY() + entidad.getH() &&
                                jugador.getY() + jugador.getH() > entidad.getY();

            if (colisionX && colisionY) {
                System.out.println("[COLISIÓN] " + jugador.getNombre() + " chocó con " + entidad.getNombre());
                if (entidad instanceof Enemigo) {
                    jugador.setVida(jugador.getVida() - 20);
                }
            }
        }
        for (EntidadVideojuego e : aEliminar) { eliminarEntidad(e); }
    }

    public void actualizar() {
        if (estadoActual != EstadoJuego.JUGANDO) return;

        List<EntidadVideojuego> copia = new ArrayList<>(entidades);
        Jugador jugadorPrincipal = null;
        for (EntidadVideojuego e : entidades) {
            if (e instanceof Jugador) { jugadorPrincipal = (Jugador) e; break; }
        }

        for (EntidadVideojuego entidad : copia) {
            if (entidad instanceof Enemigo && jugadorPrincipal != null) {
                double distancia = Math.sqrt(Math.pow(entidad.getX() - jugadorPrincipal.getX(), 2) + 
                                             Math.pow(entidad.getY() - jugadorPrincipal.getY(), 2));
                Enemigo enemigo = (Enemigo) entidad;
                if (distancia < 25.0) {
                    enemigo.setComportamientoActual("ATACAR");
                } else if (distancia <= 70.0) {
                    enemigo.setComportamientoActual("PERSEGUIR");
                    if (enemigo.getX() < jugadorPrincipal.getX()) enemigo.setX(enemigo.getX() + 2);
                    else enemigo.setX(enemigo.getX() - 2);
                } else {
                    enemigo.setComportamientoActual("PATRULLAR");
                }
            }
            entidad.actualizar();
        }
        verificarColisiones();
    }
}