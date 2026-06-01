package modelo;

public abstract class EntidadVideojuego {
    protected int x, y, w, h;
    protected String nombre;
    protected int vida;

    public EntidadVideojuego(String nombre, int x, int y, int w, int h, int vida) {
        this.nombre = nombre;
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.vida = vida;
    }
    public abstract void actualizar();
}