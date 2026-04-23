package hilos;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import monitores.*;

public class Nino extends Thread {
    private String idNino;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centrocomercial;
    private Alcantarillado alcantarillado;
    private Random r = new Random();
    private Portal portalBosque = new Portal(2);
    private Portal portalLab = new Portal(3);
    private Portal portalCC = new Portal(4);
    private Portal portalAlc = new Portal(2);
    private boolean capturado = false;
    private boolean siendoAtacado = false;
    
    public Nino(int idNumerico, Sotano psotano, Bosque pbosque, Laboratorio plaboratorio, CentroComercial pcentrocomercial, Alcantarillado palcantarillado) {
        // Esto es para que aparezcan asi N0001, N0023
        this.idNino = String.format("N%04d", idNumerico);
        this.sotano = psotano;
        this.bosque = pbosque;
        this.laboratorio = plaboratorio;
        this.centrocomercial = pcentrocomercial;
        this.alcantarillado = palcantarillado;
    }

@Override
    public void run() {
        // El ciclo se repite iterativamente mientras no esté capturado [cite: 54]
        while (!capturado) {
            try {
                // 1. Deambulan por la CALLE PRINCIPAL entre 3 y 5 segundos 
                System.out.println("El niño " + idNino + " deambula por la CALLE PRINCIPAL.");
                Thread.sleep(3000 + r.nextInt(2001));
                
                // 2. Acceden al SÓTANO BYERS y permanecen entre 1 y 2 segundos 
                // sotano.acceder(this); 
                Thread.sleep(1000 + r.nextInt(1001));

                // 3. Seleccionan un portal y van al Upside Down [cite: 60]
                int portal = r.nextInt(4);
                boolean sigueVivo = true;

                switch (portal) {
                    case 0:
                        portalBosque.cruzarHaciaUpside(this);
                        bosque.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001)); // Tiempo de recolección [cite: 63]
                        sigueVivo = bosque.salir(this);       // Intentan salir de la zona
                        if (sigueVivo) portalBosque.cruzarHaciaHawkins(this);
                        break;
                    case 1:
                        portalLab.cruzarHaciaUpside(this);
                        laboratorio.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001)); // Tiempo de recolección [cite: 63]
                        sigueVivo = laboratorio.salir(this);
                        if (sigueVivo) portalLab.cruzarHaciaHawkins(this);
                        break;
                    case 2:
                        portalCC.cruzarHaciaUpside(this);
                        centrocomercial.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001)); // Tiempo de recolección [cite: 63]
                        sigueVivo = centrocomercial.salir(this);
                        if (sigueVivo) portalCC.cruzarHaciaHawkins(this);
                        break;
                    case 3:
                        portalAlc.cruzarHaciaUpside(this);
                        alcantarillado.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001)); // Tiempo de recolección [cite: 63]
                        sigueVivo = alcantarillado.salir(this);
                        if (sigueVivo) portalAlc.cruzarHaciaHawkins(this);
                        break;
                }

                // Si el monitor devolvió false, el niño fue llevado a la colmena. Salimos del bucle.
                if (!sigueVivo) {
                    break; 
                }

                // 4. Si vuelve a Hawkins con vida, descansa en la RADIO WSQK entre 2 y 4 segundos 
                System.out.println("El niño " + idNino + " depositó la sangre y descansa en RADIO WSQK.");
                Thread.sleep(2000 + r.nextInt(2001));

            } catch (InterruptedException e) {
                System.err.println("Hilo del niño " + idNino + " interrumpido.");
                break; 
            }
        }
    }
    public String getIdNino() {
        return idNino;
    }

// ... tus atributos (capturado, siendoAtacado) ...

    public boolean isSiendoAtacado() {
        return siendoAtacado;
    }

    public void setSiendoAtacado(boolean siendoAtacado) {
        this.siendoAtacado = siendoAtacado;
    }

    public boolean isCapturado() {
        return capturado;
    }

    public void setCapturado(boolean capturado) {
        this.capturado = capturado;
    }
    /**
        public synchronized boolean serAtacado(int tiempoAtaque) {
        siendoAtacado = true;
        try {
            Thread.sleep(tiempoAtaque);
        } catch (InterruptedException e) {}
        // probabilidad 2/3 de sobrevivir
        int resiste = r.nextInt(3);
        if (resiste == 0) {
            capturado = true;
        }
        siendoAtacado = false;
        return resiste != 0;
    } **/
}