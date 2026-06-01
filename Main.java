import control.MotorJuego;
import modelo.Enemigo;
import modelo.Jugador;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SIMULADOR DE MOTOR 2D ===");
        MotorJuego motor = new MotorJuego();
        
        Jugador heroe = new Jugador("Guerrero", 10, 10);
        Enemigo orco = new Enemigo("Orco", 60, 60); // Empieza lejos (Patrullando)

        motor.agregarEntidad(heroe);
        motor.agregarEntidad(orco);
        motor.iniciarPartida();

        // TICK 1: El enemigo está lejos
        motor.actualizar();

        // Simular movimiento del jugador acercándose al enemigo
        System.out.println("[INPUT] El jugador camina hacia la derecha...");
        heroe.mover("DERECHA");
        heroe.mover("DERECHA");

        // TICK 2: El enemigo cambia a PERSEGUIR al acortar distancia
        motor.actualizar();

        // Provocar colisión física forzada
        System.out.println("[SIMULACIÓN] El orco acorrala al jugador en la misma coordenada...");
        heroe.setX(40); heroe.setY(40);
        orco.setX(40);  orco.setY(40);

        // TICK 3: Se detecta la colisión y reduce vida
        motor.actualizar();
    }
}