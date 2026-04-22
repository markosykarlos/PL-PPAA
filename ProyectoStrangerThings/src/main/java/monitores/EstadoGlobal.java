package monitores;

public class EstadoGlobal {
    // Definimos los eventos como números fijos
    public static final int NINGUNO = 0;
    public static final int APAGON_LABORATORIO = 1;
    public static final int TORMENTA_UPSIDEDOWN = 2;
    public static final int INTERVENCION_ELEVEN = 3;
    public static final int RED_MENTAL = 4;

    // Arrancamos sin evento
    private int eventoActivo = NINGUNO;

    // Método sincronizado para cambiar el evento (ESTE ES EL QUE NO ENCONTRABA)
    public synchronized void setEventoActivo(int nuevoEvento) {
        this.eventoActivo = nuevoEvento;
        notifyAll(); // Avisa a los hilos que estén esperando
    }

    // Método sincronizado para consultar el evento
    public synchronized int getEventoActivo() {
        return eventoActivo;
    }
}
