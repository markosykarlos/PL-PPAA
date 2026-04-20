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
    private CyclicBarrier barrera_bosque = new CyclicBarrier(2);
    private CyclicBarrier barrera_lab = new CyclicBarrier(3);
    private CyclicBarrier barrera_cc = new CyclicBarrier(4);
    private CyclicBarrier barrera_alcantarillado = new CyclicBarrier(2);
    
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
        try {
            sotano.acceder(idNino);
            int portal = r.nextInt(4);
            switch (portal) {
                    case 0:
                        try{
                            barrera_bosque.await();
                            bosque.acceder(idNino);
                        }
                        catch (BrokenBarrierException ex) {
                        Logger.getLogger(ninos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 1:
                        try{
                            barrera_lab.await();
                            laboratorio.acceder(idNino);
                        }
                        catch (BrokenBarrierException ex) {
                        Logger.getLogger(ninos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 2:
                        try{
                            barrera_cc.await();
                            centrocomercial.acceder(idNino);
                        }
                        catch (BrokenBarrierException ex) {
                        Logger.getLogger(ninos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;

                    case 3:
                        try{
                            barrera_alcantarillado.await();
                            alcantarillado.acceder(idNino);
                        }
                        catch (BrokenBarrierException ex) {
                        Logger.getLogger(ninos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public String getIdNino() {
        return idNino;
    }
}