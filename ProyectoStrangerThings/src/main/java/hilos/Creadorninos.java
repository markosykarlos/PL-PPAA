package hilos;

import java.util.Random;
import monitores.*;

public class Creadorninos extends Thread {
    private final int TOTAL_NINOS = 1500;
    private Random random;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centrocomercial;
    private Alcantarillado alcantarillado;
    private Portal portalBosque = new Portal(2);
    private Portal portalLab = new Portal(3);
    private Portal portalCC = new Portal(4);
    private Portal portalAlc = new Portal(2);

    public Creadorninos(Sotano psotano, Bosque pbosque, Laboratorio plaboratorio, CentroComercial pcentrocomercial, Alcantarillado palcantarillado) {
        this.random = new Random();
        this.sotano = psotano;
        this.bosque = pbosque;
        this.laboratorio = plaboratorio;
        this.centrocomercial = pcentrocomercial;
        this.alcantarillado = palcantarillado;
    }
    

    @Override
    public void run() {
        System.out.println("Iniciando la generación de niños...");
        
        for (int i = 1; i <= TOTAL_NINOS; i++) {
            Nino nuevoNino = new Nino(i, sotano, bosque, laboratorio, centrocomercial, alcantarillado, portalBosque, 
                    portalLab, portalCC, portalAlc);
            nuevoNino.start();

            try {
                int tiempoEspera = 500 + random.nextInt(1501); 
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                System.err.println("Generador de niños interrumpido.");
                break;
            }
        }
        System.out.println("¡Se han generado los 1.500 niños!");
    }
}
