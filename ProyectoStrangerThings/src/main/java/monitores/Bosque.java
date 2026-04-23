package monitores;

import hilos.Nino;
import java.util.ArrayList;
import java.util.Random;

public class Bosque {
    
    private ArrayList<Nino> ninosPresentes = new ArrayList<>();
    private Random r = new Random();

    /**
     * Entrada de Niños: Se añade a la lista de la zona[cite: 43].
     */
    public synchronized void acceder(Nino n) {
        ninosPresentes.add(n);
        System.out.println("El niño " + n.getIdNino() + " ha entrado al BOSQUE.");
    }

    /**
     * Salida de Niños: Bloquea al niño si está bajo ataque hasta que el Demogorgon termine.
     * Devuelve TRUE si logra salir vivo, o FALSE si fue capturado[cite: 68].
     */
    public synchronized boolean salir(Nino n) {
        // Mientras el niño esté siendo atacado, debe esperar 
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
            return false; // Fue llevado a la colmena [cite: 68]
        } else {
            System.out.println("El niño " + n.getIdNino() + " sale del BOSQUE con su sangre.");
            return true; // Logra salir [cite: 64]
        }
    }

    /**
     * Entrada de Demogorgons: Busca una presa aleatoria[cite: 43, 44].
     */
    public synchronized Nino buscarPresa() {
        if (ninosPresentes.isEmpty()) return null;
        
        Nino presa = ninosPresentes.get(r.nextInt(ninosPresentes.size()));

        if (!presa.isSiendoAtacado() && !presa.isCapturado()) {
            presa.setSiendoAtacado(true);
            return presa;
        }
        return null;
    }

    /**
     * Resolución del ataque: El Demogorgon avisa el resultado y despierta al niño[cite: 69].
     */
    public synchronized void resolverAtaque(Nino n, boolean capturado) {
        n.setCapturado(capturado);
        n.setSiendoAtacado(false);
        notifyAll(); // Despierta al niño bloqueado en salir()
    }
    
    public synchronized int getNumeroNinos() {
        return ninosPresentes.size();
    }
}