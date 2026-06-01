package modelo;

public abstract class EntidadVideojuego {
    protected int x, y, w, h;
    protected String nombre;
    protected int vida;

    public EntidadVideojuego(String nombre, int x, int y, int w, int h, int vida) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.vida = vida;
    }

    public abstract void actualizar();

    public int getX() { return x; }
    public void setX(int x) { this.x = x; } // <-- IMPORTANTE
    public int getY() { return y; }
    public void setY(int y) { this.y = y; } // <-- IMPORTANTE
    public int getW() { return w; }
    public int getH() { return h; }
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
}