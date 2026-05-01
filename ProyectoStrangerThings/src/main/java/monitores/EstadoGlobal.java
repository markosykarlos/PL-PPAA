package monitores;

import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstadoGlobal {
    public static final int NINGUNO = 0;
    public static final int APAGON_LABORATORIO = 1;
    public static final int TORMENTA_UPSIDEDOWN = 2;
    public static final int INTERVENCION_ELEVEN = 3;
    public static final int RED_MENTAL = 4;

    private int eventoActivo = NINGUNO;
    private long tiempoFinEvento = 0;
    private Random r = new Random();
    private boolean enPausa = false;

 public synchronized void setEventoActivo(int nuevoEvento, int duracionMs) {
        this.eventoActivo = nuevoEvento;
        
        if (nuevoEvento != NINGUNO) {
            // Ya no usamos el Random aquí, usamos la duración que nos mandan
            this.tiempoFinEvento = System.currentTimeMillis() + duracionMs;
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
    
    
    private Map<String, Integer> capturasDemogorgons = new HashMap<>();

    
    public synchronized void registrarCaptura(String idDemogorgon) {
        int actuales = capturasDemogorgons.getOrDefault(idDemogorgon, 0);
        capturasDemogorgons.put(idDemogorgon, actuales + 1);
    }

    
    public synchronized List<String> obtenerTopDemogorgons() {
        return capturasDemogorgons.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))   
                .map(entry -> entry.getKey() + " (" + entry.getValue() + " capturas)")
                .collect(Collectors.toList());
    }
    
    public synchronized void pausar() {
        enPausa = true;
    }

    public synchronized void reanudar() {
        enPausa = false;
        notifyAll();
    }

    public synchronized void chequearPausa() {
        while (enPausa) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
