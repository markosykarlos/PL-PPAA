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
    private Portal portalBosque = new Portal(2);
    private Portal portalLab = new Portal(3);
    private Portal portalCC = new Portal(4);
    private Portal portalAlc = new Portal(2);
    
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
                    break;
                case 1:
                    portalLab.cruzarHaciaUpside(this);
                    laboratorio.acceder(this);
                    break;
                case 2:
                    portalCC.cruzarHaciaUpside(this);
                    centrocomercial.acceder(this);
                    break;
                case 3:
                    portalAlc.cruzarHaciaUpside(this);
                    alcantarillado.acceder(this);
                    break;
            }
    }
    public String getIdNino() {
        return idNino;
    }
}