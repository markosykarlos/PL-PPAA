package hilos;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import monitores.*;

public class ninos extends Thread {
    private String idNino;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centrocomercial;
    private Alcantarillado alcantarillado;
    private Random r = new Random();
    private portal portalBosque = new portal(2);
    private portal portalLab = new portal(3);
    private portal portalCC = new portal(4);
    private portal portalAlc = new portal(2);
    private boolean capturado = false;
    private boolean siendoAtacado = false;
    
    public ninos(int idNumerico, Sotano psotano, Bosque pbosque, Laboratorio plaboratorio, CentroComercial pcentrocomercial, Alcantarillado palcantarillado) {
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
        // Por poner algo
        System.out.println("El niño " + idNino + " ha aparecido en la CALLE PRINCIPAL.");
        
        // Esto es para que nos diga cada seg si sigue vivo
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sotano.acceder(idNino);
        int portal = r.nextInt(4);
        switch (portal) {
                case 0:
                    portalBosque.cruzarHaciaUpside(this);
                    bosque.acceder(this);
                    try{
                        Thread.sleep(3000 + r.nextInt(2001));
                    }
                    catch(InterruptedException e) {}
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    bosque.salir(this);
                    portalBosque.cruzarHaciaHawkins(this);
                    break;
                case 1:
                    portalLab.cruzarHaciaUpside(this);
                    laboratorio.acceder(this);
                    try{
                        Thread.sleep(3000 + r.nextInt(2001));
                    }
                    catch(InterruptedException e) {}
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    laboratorio.salir(this);
                    portalLab.cruzarHaciaHawkins(this);
                    break;
                case 2:
                    portalCC.cruzarHaciaUpside(this);
                    centrocomercial.acceder(this);
                    try{
                        Thread.sleep(3000 + r.nextInt(2001));
                    }
                    catch(InterruptedException e) {}
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    centrocomercial.salir(this);
                    portalCC.cruzarHaciaHawkins(this);
                    break;
                case 3:
                    portalAlc.cruzarHaciaUpside(this);
                    alcantarillado.acceder(this);
                    try{
                        Thread.sleep(3000 + r.nextInt(2001));
                    }
                    catch(InterruptedException e) {}
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    alcantarillado.salir(this);
                    portalAlc.cruzarHaciaHawkins(this);
                    break;
            }
    }
    public String getIdNino() {
        return idNino;
    }
    
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
    }
}