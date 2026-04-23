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
    private Portal portalBosque = new Portal(2);
    private Portal portalLab = new Portal(3);
    private Portal portalCC = new Portal(4);
    private Portal portalAlc = new Portal(2);
    private Random r = new Random();
    
    // Estos deberían ser compartidos (pasados por constructor), aquí los pongo como referencia
    //private Portal portalBosque = new Portal(2); 

    private boolean capturado = false;
    private boolean siendoAtacado = false;
    
    public Nino(int idNum, Sotano s, Bosque b, Laboratorio l, CentroComercial c, Alcantarillado a) {
        this.idNino = String.format("N%04d", idNum);
        this.sotano = s;
        this.bosque = b;
        this.laboratorio = l;
        this.centrocomercial = c;
        this.alcantarillado = a;
    }

    @Override
    public void run() {
        while (!capturado) {
            try {
                Thread.sleep(3000 + r.nextInt(2001));
                sotano.acceder(this);
                 int portal = r.nextInt(4);
                boolean sigueVivo = true;
                switch (portal) {
                    case 0:
                        portalBosque.cruzarHaciaUpside(this);
                        bosque.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001));
                        sigueVivo = bosque.salir(this);
                        if (sigueVivo){portalBosque.cruzarHaciaHawkins(this);}
                        break;
                    case 1:
                        portalLab.cruzarHaciaUpside(this);
                        laboratorio.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001));
                        sigueVivo = laboratorio.salir(this);
                        if (sigueVivo){portalLab.cruzarHaciaHawkins(this);}
                        break;
                    case 2:
                        portalCC.cruzarHaciaUpside(this);
                        centrocomercial.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001));
                        sigueVivo = centrocomercial.salir(this);
                        if (sigueVivo){portalCC.cruzarHaciaHawkins(this);}
                        break;
                    case 3:
                        portalAlc.cruzarHaciaUpside(this);
                        alcantarillado.acceder(this);
                        Thread.sleep(3000 + r.nextInt(2001));
                        sigueVivo = alcantarillado.salir(this);
                        if (sigueVivo){portalAlc.cruzarHaciaHawkins(this);}
                        break;
                if (!vive) break;
                Thread.sleep(2000 + r.nextInt(2001));
                }
            } catch (InterruptedException e) { break; }
        }
    }

    public String getIdNino() { return idNino; }
    public boolean isSiendoAtacado() { return siendoAtacado; }
    public void setSiendoAtacado(boolean b) { siendoAtacado = b; }
    public boolean isCapturado() { return capturado; }
    public void setCapturado(boolean b) { capturado = b; }
}