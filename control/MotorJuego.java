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
            System.out.println("[SISTEMA] El motor está en reposo (MENU/PAUSA/GAME_OVER).");
            return;
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