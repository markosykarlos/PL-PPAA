package monitores;

import hilos.Nino;
import java.util.ArrayList;
import java.util.List;

public class Colmena {
    private List<Nino> ninosCapturados = new ArrayList<>();

    public synchronized void depositarNino(Nino n) {
        ninosCapturados.add(n);
        System.out.println("El niño " + n.getIdNino() + " ha sido llevado a la colmena. Total: " + ninosCapturados.size());
    }

    public synchronized int getTotalNinos() {
        return ninosCapturados.size();
    }
    
    // NUEVO: Método para el evento de Eleven
    public synchronized List<Nino> liberarNinos(int cantidadMaxima) {
        List<Nino> liberados = new ArrayList<>();
        int aLiberar = Math.min(cantidadMaxima, ninosCapturados.size());
        
        for (int i = 0; i < aLiberar; i++) {
            Nino n = ninosCapturados.remove(0);
            liberados.add(n);
        }
        return liberados;
    }
    public synchronized String getIDs() {
        if (ninosCapturados.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Nino n : ninosCapturados) sb.append(n.getIdNino()).append(", ");
        return sb.substring(0, sb.length() - 2);
    }
}