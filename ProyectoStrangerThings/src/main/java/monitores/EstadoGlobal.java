package monitores;

import java.util.Random;

public class EstadoGlobal {
    public static final int NINGUNO = 0;
    public static final int APAGON_LABORATORIO = 1;
    public static final int TORMENTA_UPSIDEDOWN = 2;
    public static final int INTERVENCION_ELEVEN = 3;
    public static final int RED_MENTAL = 4;

    private int eventoActivo = NINGUNO;
    private long tiempoFinEvento = 0;
    private Random r = new Random();

    public synchronized void setEventoActivo(int nuevoEvento) {
        this.eventoActivo = nuevoEvento;
        
        if (nuevoEvento != NINGUNO) {
            // Duración aleatoria entre 5 y 10 segundos (5000 - 10000 ms)
            int duracion = 5000 + r.nextInt(5001);
            this.tiempoFinEvento = System.currentTimeMillis() + duracion;
        } else {
            this.tiempoFinEvento = 0;
        }
        
        notifyAll();
    }

    public synchronized int getEventoActivo() {
        return eventoActivo;
    }
    
    /**
     * Método clave para la Parte 2 (RMI)
     * Devuelve una descripción legible del evento + tiempo restante
     */
    public synchronized String getDescripcionEventoConTiempo() {
        if (eventoActivo == NINGUNO) {
            return "Sin evento activo";
        }

        String nombreEvento = switch (eventoActivo) {
            case APAGON_LABORATORIO -> "APAGÓN DEL LABORATORIO";
            case TORMENTA_UPSIDEDOWN -> "TORMENTA DEL UPSIDE DOWN";
            case INTERVENCION_ELEVEN -> "INTERVENCIÓN DE ELEVEN";
            case RED_MENTAL -> "LA RED MENTAL";
            default -> "Evento desconocido";
        };

        long tiempoRestante = tiempoFinEvento - System.currentTimeMillis();
        
        if (tiempoRestante <= 0) {
            return nombreEvento + " (finalizando...)";
        }

        return nombreEvento + " - " + (tiempoRestante / 1000) + "s restantes";
    }

    /**
     * Método auxiliar útil para saber si hay un evento activo
     */
    public synchronized boolean hayEventoActivo() {
        return eventoActivo != NINGUNO;
    }
}
