package monitores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadoGlobal {
    public static final int NINGUNO = 0;
    public static final int APAGON_LABORATORIO = 1;
    public static final int TORMENTA_UPSIDEDOWN = 2;
    public static final int INTERVENCION_ELEVEN = 3;
    public static final int RED_MENTAL = 4;

    private int eventoActivo = NINGUNO;
    private long tiempoFinEvento = 0;
    private boolean enPausa = false;

 public synchronized void setEventoActivo(int nuevoEvento, int duracionMs) {
        this.eventoActivo = nuevoEvento;
        if (nuevoEvento != NINGUNO) {
            this.tiempoFinEvento = System.currentTimeMillis() + duracionMs;
        }
        else {
            this.tiempoFinEvento = 0;
        }
        notifyAll();
    }

    public synchronized int getEventoActivo() {
        return eventoActivo;
    }
    
     // Método para la Parte 2
     // Devuelve una descripción del evento + tiempo restante
    public synchronized String getDescripcionEventoConTiempo() {
        if (eventoActivo == NINGUNO) {
            return "Sin evento activo";
        }
        String nombreEvento = switch (eventoActivo) {
            case APAGON_LABORATORIO -> "APAGON DEL LABORATORIO";
            case TORMENTA_UPSIDEDOWN -> "TORMENTA DEL UPSIDE DOWN";
            case INTERVENCION_ELEVEN -> "INTERVENCION DE ELEVEN";
            case RED_MENTAL -> "LA RED MENTAL";
            default -> "Evento desconocido";
        };
        long tiempoRestante = tiempoFinEvento - System.currentTimeMillis();
        if (tiempoRestante <= 0) {
            return nombreEvento + " (finalizando)";
        }
        return nombreEvento + " - " + (tiempoRestante / 1000) + "s restantes";
    }

    public synchronized boolean hayEventoActivo() {
        return eventoActivo != NINGUNO;
    }
    
    private Map<String, Integer> capturasDemogorgons = new HashMap<>();
    
    public synchronized void registrarCaptura(String idDemogorgon) {
        int actuales = capturasDemogorgons.getOrDefault(idDemogorgon, 0);
        capturasDemogorgons.put(idDemogorgon, actuales + 1);
    }
    
   public synchronized List<String> obtenerTopDemogorgons() {
    List<String> top = new ArrayList<>();
    List<Integer> capturasTop = new ArrayList<>();
    for (String id : capturasDemogorgons.keySet()) {
        int capturas = capturasDemogorgons.get(id);
        int posicion = 0;
        // Buscar dónde insertar según nº de capturas
        while (posicion < capturasTop.size() && capturasTop.get(posicion) >= capturas) {
            posicion++;
        }
        // Insertar en la posición correcta
        top.add(posicion, id + " (" + capturas + " capturas)");
        capturasTop.add(posicion, capturas);
        // Mantener solo top 3
        if (top.size() > 3) {
            top.remove(3);
            capturasTop.remove(3);
        }
    }
    return top;
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
