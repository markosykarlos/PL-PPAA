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
            Nino nuevoNino = new Nino(i, sotano, bosque, laboratorio, centrocomercial, alcantarillado);
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
