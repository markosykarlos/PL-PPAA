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
        System.out.println("El nino " + n.getIdNino() + " ha entrado al centro comercial.");
    }

    public synchronized boolean salir(Nino n) {
        while (n.isSiendoAtacado()) {
            try {
                System.out.println("El nino " + n.getIdNino() + " intenta huir del centro comercial pero esta bajo ataque");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        ninosPresentes.remove(n);
        if (n.isCapturado()) {
            return false;
        } else {
            System.out.println("El nino " + n.getIdNino() + " sale del centro comercial con su sangre.");
            return true;
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
        notifyAll();
    }

    public synchronized int getNumeroNinos() {
        return ninosPresentes.size();
    }
    
    public int getNumeroDemogorgons() {
        return demogorgonsPresentes.size();
    }
}