package monitores;

public class EstadoGlobal {
    public static final int NINGUNO = 0;
    public static final int APAGON_LABORATORIO = 1;
    public static final int TORMENTA_UPSIDEDOWN = 2;
    public static final int INTERVENCION_ELEVEN = 3;
    public static final int RED_MENTAL = 4;

    private int eventoActivo = NINGUNO;

    public synchronized void setEventoActivo(int nuevoEvento) {
        this.eventoActivo = nuevoEvento;
        notifyAll(); 
    }

    public synchronized int getEventoActivo() {
        return eventoActivo;
    }
}
