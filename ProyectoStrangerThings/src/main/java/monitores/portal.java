package monitores;

import hilos.Nino;
import java.util.ArrayList;

public class Portal {
    private int capacidad;
    private int esperandoIda = 0;
    private int esperandoVuelta = 0;
    private boolean ocupado = false;
    private EstadoGlobal estadoGlobal;
    
    // Listas para la interfaz gráfica
    private ArrayList<String> enTransito = new ArrayList<>();
    private ArrayList<String> ninosEsperando = new ArrayList<>(); 
    
    private ArrayList<Integer> gruposListos = new ArrayList<>();
    private int contador = 0;
    private int grupoactual = 0;
    private int grupoCruzado = 0;

    public Portal(int capacidad, EstadoGlobal estadoGlobal) {
        this.capacidad = capacidad;
        this.estadoGlobal = estadoGlobal;
    }

    public void cruzarHaciaUpside(Nino n) {
        // ESO ES PARA SOLO PONER UNA PARTE DEL CODIGO EN SYNCHRONIZED
        synchronized(this) {
            esperandoIda++;
            contador++;                                   
            n.setGrupoportal(grupoactual);
            
            // Lo añadimos a la cola visible en la UI
            ninosEsperando.add(n.getIdNino() + "(G" + grupoactual + ")");

            if (contador % capacidad == 0) {
                gruposListos.add(grupoactual);
                grupoactual++;
                notifyAll(); 
            }
            
            while (esperandoVuelta > 0 || 
                   gruposListos.isEmpty() || 
                   n.getGrupoportal() != gruposListos.get(0) ||
                   ocupado ||
                   estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            ocupado = true;
            esperandoIda--; 
            
            // Pasa de "esperando" a "en tránsito"
            ninosEsperando.remove(n.getIdNino() + "(G" + n.getGrupoportal() + ")");
            enTransito.add(n.getIdNino() + "(->)");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        synchronized(this) {
            grupoCruzado++;
            ocupado = false;
            enTransito.remove(n.getIdNino() + "(->)");
            
            if (grupoCruzado == capacidad) {
                gruposListos.remove(0); 
                grupoCruzado = 0;
            }
            notifyAll(); 
        }
    }

    public void cruzarHaciaHawkins(Nino n) {
        synchronized(this) {
            esperandoVuelta++;
            ninosEsperando.add(n.getIdNino() + "(VUELTA)");
            
            while (ocupado || estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            ocupado = true;
            esperandoVuelta--; 
            
            ninosEsperando.remove(n.getIdNino() + "(VUELTA)");
            enTransito.add(n.getIdNino() + "(<-)");
        } 

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        synchronized(this) {
            ocupado = false;
            enTransito.remove(n.getIdNino() + "(<-)");
            notifyAll();
        }
    }

    // Nuevo getIDs() para mostrar quién espera y quién cruza
public synchronized String getIDs() {
    String textoEsperando;
    String textoCruzando;

    // Comprobamos la cola de espera
    if (ninosEsperando.isEmpty()) {
        textoEsperando = "Nadie";
    } else {
        textoEsperando = String.join(", ", ninosEsperando);
    }

    // Comprobamos los que están cruzando
    if (enTransito.isEmpty()) {
        textoCruzando = "Libre";
    } else {
        textoCruzando = String.join(", ", enTransito);
    }

    return "Cola: " + textoEsperando + "\nCruzando: " + textoCruzando;
}

    public synchronized int getNumeroNinosEsperando() {
        return esperandoIda + esperandoVuelta + enTransito.size();
    }
}