package modelo;

public class Jugador extends EntidadVideojuego {
    private int puntuacion;

    public Jugador(String nombre, int x, int y) {
        // Le pasamos al constructor padre: nombre, x, y, ancho(w), alto(h), vida
        super(nombre, x, y, 16, 16, 100);
        this.puntuacion = 0;
    }

    @Override
    public void actualizar() {
        // El jugador se actualiza por inputs, lo dejamos listo para el bucle
    }

    public void mover(String direccion) {
        switch (direccion.toUpperCase()) {
            case "ARRIBA": y -= 5; break;
            case "ABAJO": y += 5; break;
            case "IZQUIERDA": x -= 5; break;
            case "DERECHA": x += 5; break;
        }
        System.out.println("[LOG - Jugador] Movido a (" + x + "," + y + ")");
    }

    public int getPuntuacion() { return puntuacion; }
    public void sumarPuntos(int puntos) { this.puntuacion += puntos; }
}