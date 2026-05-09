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

        List<Map.Entry<String, Integer>> lista = new java.util.ArrayList<>(capturasDemogorgons.entrySet());

        
        lista.sort(new java.util.Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        
        List<String> resultado = new java.util.ArrayList<>();
        
     
        for (Map.Entry<String, Integer> entrada : lista) {
            String id = entrada.getKey();
            Integer numCapturas = entrada.getValue();
            
            
            resultado.add(id + " (" + numCapturas + " capturas)");
            
            // la practica ponia top 3
            if (resultado.size() == 3) {
                break;
            }
        }

        return resultado;
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
