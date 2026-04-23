package monitores;

import hilos.Nino;
import java.util.ArrayList;
import java.util.Random;

public class Laboratorio {
    
    private ArrayList<Nino> ninosPresentes = new ArrayList<>();
    private Random r = new Random();

    public synchronized void acceder(Nino n) {
        ninosPresentes.add(n);
        System.out.println("El niño " + n.getIdNino() + " ha entrado al LABORATORIO.");
    }

    public synchronized boolean salir(Nino n) {
        // Bloqueo si hay ataque en curso
        while (n.isSiendoAtacado()) {
            try {
                System.out.println("El niño " + n.getIdNino() + " intenta escapar del LABORATORIO pero está bajo ataque...");
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        ninosPresentes.remove(n);

        if (n.isCapturado()) {
            return false; // Capturado y llevado a la Colmena
        } else {
            System.out.println("El niño " + n.getIdNino() + " sale del LABORATORIO con su sangre.");
            return true; // Regresa a Hawkins
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
        notifyAll(); // Despierta al niño
    }
    
    public synchronized int getNumeroNinos() {
        return ninosPresentes.size();
    }
}