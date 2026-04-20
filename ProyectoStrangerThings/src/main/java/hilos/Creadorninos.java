package hilos;

import java.util.Random;

public class Creadorninos extends Thread {
    private final int TOTAL_NINOS = 1500;
    private Random random;

    public Creadorninos() {
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("Iniciando la generación de niños...");
        
        for (int i = 1; i <= TOTAL_NINOS; i++) {
            ninos nuevoNino = new ninos(i);
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
