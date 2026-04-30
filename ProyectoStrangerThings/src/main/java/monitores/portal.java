package monitores;

import hilos.Nino;
import java.util.ArrayList;

public class Portal {
    private int capacidad;
    private int esperandoIda = 0;
    private int cruzandoIda = 0; 
    private int esperandoVuelta = 0;
    private boolean ocupado = false;
    private EstadoGlobal estadoGlobal;
    private ArrayList<String> enTransito = new ArrayList<>();

    public Portal(int capacidad, EstadoGlobal estadoGlobal) {
        this.capacidad = capacidad;
        this.estadoGlobal = estadoGlobal;
    }

    public synchronized void cruzarHaciaUpside(Nino n) {
        try {
            esperandoIda++;
            enTransito.add(n.getIdNino() + "(->)");

          
            while (esperandoIda < capacidad || esperandoVuelta > 0 || cruzandoIda > 0 || estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO) {
                
                
                if (esperandoIda >= capacidad && esperandoVuelta == 0 && cruzandoIda == 0 && estadoGlobal.getEventoActivo() != EstadoGlobal.APAGON_LABORATORIO) {
                    cruzandoIda = capacidad; 
                    esperandoIda -= capacidad; 
                    notifyAll(); 
                    break;
                }
                wait();
            }

            
            while (ocupado || estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO || esperandoVuelta > 0) {
                wait();
            }

            ocupado = true;
            Thread.sleep(1000); 
            cruzandoIda--; 
            ocupado = false;
            
            enTransito.remove(n.getIdNino() + "(->)");
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
}