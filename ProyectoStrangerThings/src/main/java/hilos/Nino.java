package hilos;

import java.util.Random;
import monitores.*;

public class Nino extends Thread {
    private String idNino;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centrocomercial;
    private Alcantarillado alcantarillado;
    private Random r = new Random();
    
    // Estos deberían ser compartidos (pasados por constructor), aquí los pongo como referencia
    private Portal portalBosque = new Portal(2); 

    private boolean capturado = false;
    private boolean siendoAtacado = false;
    
    public Nino(int idNum, Sotano s, Bosque b, Laboratorio l, CentroComercial c, Alcantarillado a) {
        this.idNino = String.format("N%04d", idNum); // [cite: 52]
        this.sotano = s;
        this.bosque = b;
        this.laboratorio = l;
        this.centrocomercial = c;
        this.alcantarillado = a;
    }

    @Override
    public void run() {
        while (!capturado) { // [cite: 54]
            try {
                // 1. Calle Principal [cite: 72]
                Thread.sleep(3000 + r.nextInt(2001));
                
                // 2. Sótano Byers [cite: 58]
                // sotano.acceder(this); 
                Thread.sleep(1000 + r.nextInt(1001));

                // 3. Upside Down [cite: 60, 63]
                int p = r.nextInt(4);
                boolean vive = true;
                if (p == 0) {
                    bosque.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001)); 
                    vive = bosque.salir(this); // Bloqueo si hay ataque 
                } else if (p == 1) {
                    laboratorio.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    vive = laboratorio.salir(this);
                } else if (p == 2) {
                    centrocomercial.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    vive = centrocomercial.salir(this);
                } else {
                    alcantarillado.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    vive = alcantarillado.salir(this);
                }

                if (!vive) break; // Fin del hilo si es capturado [cite: 68]

                // 4. Radio WSQK [cite: 71]
                Thread.sleep(2000 + r.nextInt(2001));

            } catch (InterruptedException e) { break; }
        }
    }

    public String getIdNino() { return idNino; }
    public boolean isSiendoAtacado() { return siendoAtacado; }
    public void setSiendoAtacado(boolean b) { siendoAtacado = b; }
    public boolean isCapturado() { return capturado; }
    public void setCapturado(boolean b) { capturado = b; }
}