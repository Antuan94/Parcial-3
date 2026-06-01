package control;

public class MotorJuego {
    public enum EstadoJuego { MENU, JUGANDO, PAUSA, GAME_OVER }
    private EstadoJuego estadoActual = EstadoJuego.MENU;

    public void actualizar() {
        System.out.println("[MOTOR] Actualizando ciclo en estado: " + estadoActual);
    }
}