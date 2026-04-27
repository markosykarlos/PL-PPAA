package monitores;

public class Sangre {
    private int cantidad = 0;

    public synchronized void anadirSangre() {
        cantidad++;
    }

    public synchronized void usarSangre(int cantidadAUsar) {
        if (cantidad >= cantidadAUsar) {
            cantidad -= cantidadAUsar;
        } else {
            cantidad = 0;
        }
    }

    public synchronized int getCantidad() {
        return cantidad;
    }
}
