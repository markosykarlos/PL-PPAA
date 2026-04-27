package monitores;

import hilos.Nino;
import java.util.ArrayList;
import java.util.Random;

public class CentroComercial {
    private ArrayList<Nino> ninosPresentes = new ArrayList<>();
    private Random r = new Random();
    private ArrayList<String> demogorgonsPresentes = new ArrayList<>();

    public synchronized void accederDemogorgon(String id) { demogorgonsPresentes.add(id); }
    public synchronized void salirDemogorgon(String id) { demogorgonsPresentes.remove(id); }

    // Reemplaza tu getNumeroNinos() por estos dos métodos:
    public synchronized String getIDsNinos() {
        if (ninosPresentes.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (hilos.Nino n : ninosPresentes) sb.append(n.getIdNino()).append(", ");
        return sb.substring(0, sb.length() - 2);
    }
    
    public synchronized String getIDsDemogorgons() {
        return String.join(", ", demogorgonsPresentes);
    }

    public synchronized void acceder(Nino n) {
        ninosPresentes.add(n);
        System.out.println("El niño " + n.getIdNino() + " ha entrado al BOSQUE.");
    }

    public synchronized boolean salir(Nino n) {
        while (n.isSiendoAtacado()) {
            try {
                System.out.println("El niño " + n.getIdNino() + " intenta huir del BOSQUE pero está bajo ataque...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        ninosPresentes.remove(n);
        
        if (n.isCapturado()) {
            return false; // Fue llevado a la colmena
        } else {
            System.out.println("El niño " + n.getIdNino() + " sale del BOSQUE con su sangre.");
            return true; // Logra salir
        }
    }

    public synchronized Nino buscarPresa() {
        if (ninosPresentes.isEmpty()) return null;
        Nino presa = ninosPresentes.get(r.nextInt(ninosPresentes.size()));
        
        if (!presa.isSiendoAtacado() && !presa.isCapturado()) {
            presa.setSiendoAtacado(true);
            return presa;
        }
        return null;
    }

    public synchronized void resolverAtaque(Nino n, boolean capturado) {
        n.setCapturado(capturado);
        n.setSiendoAtacado(false);
        notifyAll(); // Despierta al niño bloqueado en salir()
    }

    public synchronized int getNumeroNinos() {
        return ninosPresentes.size();
    }
}