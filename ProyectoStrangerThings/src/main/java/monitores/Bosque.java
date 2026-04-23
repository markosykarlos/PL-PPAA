package monitores;

import hilos.Nino;
import java.util.ArrayList;
import java.util.Random;

public class Bosque {
    
    // Lista de inquilinos
    private ArrayList<Nino> ninosPresentes = new ArrayList<>();
    private Random r = new Random();

    /**
     * Entrada de Niños: El niño entra y se añade a la lista.
     */
    public synchronized void acceder(Nino n) {
        ninosPresentes.add(n);
        System.out.println("El niño " + n.getIdNino() + " ha entrado al BOSQUE.");
    }

    /**
     * Salida de Niños: Comprueba si puede salir o si debe esperar a que termine un ataque.
     * Devuelve TRUE si logra salir vivo, o FALSE si fue capturado.
     */
    public synchronized boolean salir(Nino n) {
        // BLOQUEO MUTUO: Si el demogorgon lo está atacando, el niño NO puede salir.
        // Se queda bloqueado (wait) hasta que el demogorgon termine el ataque.
        while (n.isSiendoAtacado()) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                System.err.println("Niño " + n.getIdNino() + " interrumpido mientras esperaba el fin del ataque.");
            }
        }

        // Una vez que no está siendo atacado (o si nunca lo fue), comprobamos su estado
        if (n.isCapturado()) {
            ninosPresentes.remove(n);
            return false; // El niño no sale, fue llevado a la colmena
        } else {
            ninosPresentes.remove(n);
            System.out.println("El niño " + n.getIdNino() + " sale del BOSQUE con su sangre.");
            return true; // Logra salir sano y salvo
        }
    }

    /**
     * Entrada de Demogorgons: Busca una presa aleatoria en la lista.
     */
    public synchronized Nino buscarPresa() {
        if (ninosPresentes.isEmpty()) {
            return null; // No hay niños
        }
        
        // Elegir un niño aleatorio
        int index = r.nextInt(ninosPresentes.size());
        Nino presa = ninosPresentes.get(index);

        // Si el niño no está siendo atacado ya por otro demogorgon ni capturado, lo marcamos
        if (!presa.isSiendoAtacado() && !presa.isCapturado()) {
            presa.setSiendoAtacado(true);
            return presa;
        }
        
        return null;
    }

    /**
     * Resolución del ataque: Llamado por el Demogorgon cuando termina su sleep().
     */
    public synchronized void resolverAtaque(Nino n, boolean capturado) {
        n.setCapturado(capturado);
        n.setSiendoAtacado(false); // Liberamos el bloqueo
        notifyAll(); // Despierta al niño si estaba atascado en el wait() del método salir()
    }
    
    // Dentro de Bosque.java, Laboratorio.java, etc.

public synchronized boolean salir(Nino n) {
    // Mientras el niño esté siendo atacado, no puede salir de la zona
    // La duración la marca el demogorgón 
    while (n.isSiendoAtacado()) {
        try {
            System.out.println("El niño " + n.getIdNino() + " está intentando huir pero está bajo ataque...");
            wait(); // El hilo del niño se queda esperando aquí
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Una vez liberado del ataque (o si no hubo ataque)
    ninosPresentes.remove(n); // Se quita de la lista de la zona

    if (n.isCapturado()) {
        // Si el resultado del ataque fue captura, el niño no vuelve a Hawkins [cite: 68]
        return false; 
    } else {
        // Si no fue capturado, puede proceder al portal de regreso [cite: 64]
        return true;
    }
}
    /**
     * Útil para cuando el Demogorgon necesite saber qué zona tiene más niños (La Red Mental)
     */
    public synchronized int getNumeroNinos() {
    }
}