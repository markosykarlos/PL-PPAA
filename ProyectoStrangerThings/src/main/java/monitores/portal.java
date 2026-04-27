package monitores;

import hilos.*;

public class Portal {
    private int capacidad;
    private int esperandoIda = 0;
    private int esperandoVuelta = 0;
    private boolean ocupado = false;
    private EstadoGlobal estadoGlobal;
    private java.util.ArrayList<String> enTransito = new java.util.ArrayList<>();

    public Portal(int capacidad, EstadoGlobal estadoGlobal) {
        this.capacidad = capacidad;
        this.estadoGlobal = estadoGlobal;
    }

    public synchronized void cruzarHaciaUpside(Nino n) {
        try {
            esperandoIda++;
            enTransito.add(n.getIdNino() + "(->)");
            while (estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO || esperandoVuelta > 0 || ocupado || esperandoIda < capacidad) {
                if (esperandoIda >= capacidad && esperandoVuelta == 0 && !ocupado && estadoGlobal.getEventoActivo() != EstadoGlobal.APAGON_LABORATORIO) break;
                wait();
            }
            ocupado = true;
            esperandoIda--;
            Thread.sleep(1000);
            ocupado = false;
            enTransito.remove(n.getIdNino() + "(->)");
            notifyAll();
        } catch (InterruptedException e) {}
    }

    public synchronized void cruzarHaciaHawkins(Nino n) {
        try {
            esperandoVuelta++;
            enTransito.add(n.getIdNino() + "(<-)");
            while (estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO || ocupado) wait();
            ocupado = true;
            esperandoVuelta--;
            Thread.sleep(1000);
            ocupado = false;
            enTransito.remove(n.getIdNino() + "(<-)");
            notifyAll();
        } catch (InterruptedException e) {}
    }

    public synchronized String getIDs() {
        return String.join(", ", enTransito);
    }
}