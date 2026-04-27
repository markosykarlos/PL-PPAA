package hilos;

import monitores.*;
import java.util.Random;

public class Nino extends Thread {
    private String idNino;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centrocomercial;
    private Alcantarillado alcantarillado;
    
    private Portal portalBosque;
    private Portal portalLab;
    private Portal portalCC;
    private Portal portalAlc;
    
    private EstadoGlobal estadoGlobal;
    private Sangre sangre;
    
    private ZonaHawkins callePrincipal;
    private ZonaHawkins radioWSQK;
    
    private Random r = new Random();
    private boolean capturado = false;
    private boolean siendoAtacado = false;

    // Constructor actualizado con TODAS las dependencias
    public Nino(int idNumerico, Sotano psotano, Bosque pbosque, Laboratorio plaboratorio, CentroComercial pcentrocomercial, Alcantarillado palcantarillado, Portal pportalBosque, Portal pportalLab, Portal pportalCC, Portal pportalAlc, EstadoGlobal e, Sangre san, ZonaHawkins calle, ZonaHawkins radio) {
        this.idNino = String.format("N%04d", idNumerico);
        this.sotano = psotano;
        this.bosque = pbosque;
        this.laboratorio = plaboratorio;
        this.centrocomercial = pcentrocomercial;
        this.alcantarillado = palcantarillado;
        this.portalBosque = pportalBosque;
        this.portalLab = pportalLab;
        this.portalCC = pportalCC;
        this.portalAlc = pportalAlc;
        this.estadoGlobal = e;
        this.sangre = san;
        this.callePrincipal = calle;
        this.radioWSQK = radio;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. Sótano Byers
                sotano.acceder(idNino);
                Thread.sleep(1000 + r.nextInt(1001));
                sotano.salir(idNino);

                // 2. Elegir portal y zona
                int portalElegido = r.nextInt(4);
                boolean sigueVivo = true;

                switch (portalElegido) {
                    case 0:
                        portalBosque.cruzarHaciaUpside(this);
                        bosque.acceder(this); recolectarSangre(); sigueVivo = bosque.salir(this);
                        if (sigueVivo) portalBosque.cruzarHaciaHawkins(this); 
                        break;
                    case 1:
                        portalLab.cruzarHaciaUpside(this);
                        laboratorio.acceder(this); recolectarSangre(); sigueVivo = laboratorio.salir(this);
                        if (sigueVivo) portalLab.cruzarHaciaHawkins(this); 
                        break;
                    case 2:
                        portalCC.cruzarHaciaUpside(this);
                        centrocomercial.acceder(this); recolectarSangre(); sigueVivo = centrocomercial.salir(this);
                        if (sigueVivo) portalCC.cruzarHaciaHawkins(this); 
                        break;
                    case 3:
                        portalAlc.cruzarHaciaUpside(this);
                        alcantarillado.acceder(this); recolectarSangre(); sigueVivo = alcantarillado.salir(this);
                        if (sigueVivo) portalAlc.cruzarHaciaHawkins(this); 
                        break;
                }

                // Si fue capturado, espera aquí hasta que Eleven lo rescate
                if (!sigueVivo) {
                    esperarRescate();
                } else {
                    // 3. Regreso exitoso: Depositar sangre y Radio
                    sangre.anadirSangre();
                    radioWSQK.entrar(idNino);
                    Thread.sleep(2000 + r.nextInt(2001)); // Entre 2 y 4 segundos
                    radioWSQK.salir(idNino);
                }

                // 4. Calle Principal (tanto si vuelve sano como si es rescatado por Eleven)
                callePrincipal.entrar(idNino);
                Thread.sleep(3000 + r.nextInt(2001)); // Entre 3 y 5 segundos
                callePrincipal.salir(idNino);

            } catch (InterruptedException e) {
                System.out.println(idNino + " ha sido interrumpido.");
            }
        }
    }

    private void recolectarSangre() throws InterruptedException {
        int tiempoRecoleccion = 3000 + r.nextInt(2001); // 3 a 5 segundos
        if (estadoGlobal.getEventoActivo() == EstadoGlobal.TORMENTA_UPSIDEDOWN) {
            tiempoRecoleccion *= 2; // Tormenta duplica el tiempo
        }
        Thread.sleep(tiempoRecoleccion);
    }

    public synchronized boolean serAtacado(int tiempoAtaque) {
        siendoAtacado = true;
        try {
            Thread.sleep(tiempoAtaque);
        } catch (InterruptedException e) {}
        
        boolean resiste = (r.nextInt(3) != 2); 
        if (!resiste) {
            capturado = true;
        }
        return resiste;
    }
    
    private synchronized void esperarRescate() throws InterruptedException {
        while (capturado) {
            wait();
        }
    }
    
    public synchronized void serRescatado() {
        this.capturado = false;
        notifyAll();
    }

    
    public String getIdNino() { return idNino; }
    public boolean isSiendoAtacado() { return siendoAtacado; }
    public void setSiendoAtacado(boolean b) { siendoAtacado = b; }
    public boolean isCapturado() { return capturado; }
    public void setCapturado(boolean b) { capturado = b; }
}