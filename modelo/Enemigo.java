package modelo;

public class Enemigo extends EntidadVideojuego {
    private String comportamientoActual;

    public Enemigo(String nombre, int x, int y) {
        super(nombre, x, y, 16, 16, 40);
        this.comportamientoActual = "PATRULLAR";
    }

    @Override
    public void actualizar() {
        // En el core muestra un log básico; en la rama de avanzadas le meteremos la IA por distancia
        System.out.println("[LOG - " + nombre + "] Estado: " + comportamientoActual + " en (" + x + "," + y + ")");
    }

    public String getComportamientoActual() { return comportamientoActual; }
    public void setComportamientoActual(String comportamiento) { this.comportamientoActual = comportamiento; }
}