package monitores;

import hilos.Nino;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

public class Portal {
    private int capacidad;
    private int esperandoIda = 0;
    private int cruzandoIda = 0; 
    private int esperandoVuelta = 0;
    private boolean ocupado = false;
    private EstadoGlobal estadoGlobal;
    private ArrayList<String> enTransito = new ArrayList<>();
    private ArrayList<Integer> gruposListos = new ArrayList<>();
    private int contador = 0;
    private int grupoactual = 0;
    int grupoCruzado = 0;

    public Portal(int capacidad, EstadoGlobal estadoGlobal) {
        this.capacidad = capacidad;
        this.estadoGlobal = estadoGlobal;
    }

    public synchronized void cruzarHaciaUpside(Nino n) {
    try {
        esperandoIda++;
        contador++;                                   
        n.setGrupoportal(grupoactual);

        if (contador % capacidad == 0) {
            gruposListos.add(grupoactual);
            grupoactual++;
        }
        
        enTransito.add(n.getIdNino() + "(->)");

        while (esperandoVuelta > 0 || 
               gruposListos.isEmpty() || 
               n.getGrupoportal() != gruposListos.get(0) || ocupado ||
               estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO) {
            wait();
        }

        while (ocupado) {
            wait();
        }
        
        ocupado = true;
        System.out.println(n.getIdNino() + " cruzando hacia Upside Down...");
        Thread.sleep(1000);
        
        grupoCruzado++;
        ocupado = false;
        enTransito.remove(n.getIdNino() + "(->)");
        
        if (grupoCruzado == capacidad) {
            gruposListos.remove(0);
            grupoCruzado = 0;
            System.out.println("Grupo completado y removido de la lista");
        }
        
        notifyAll();
        
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

    public synchronized void cruzarHaciaHawkins(Nino n) {
        try {
            esperandoVuelta++;
            enTransito.add(n.getIdNino() + "(<-)");

            
            while (ocupado || estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO) {
                wait();
            }

            ocupado = true;
            esperandoVuelta--; 
            Thread.sleep(1000);
            ocupado = false;
            
            enTransito.remove(n.getIdNino() + "(<-)");
            notifyAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized String getIDs() {
        if (enTransito.isEmpty()) return "";
        return String.join(", ", enTransito);
    }

    public synchronized int getNumeroNinosEsperando() {
        return esperandoIda;
    }
}