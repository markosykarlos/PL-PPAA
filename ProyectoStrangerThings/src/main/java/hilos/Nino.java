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
    private Portal portalBosque; 
    private Portal portalLab;
    private Portal portalCC;
    private Portal portalAlc;
    private boolean capturado = false;
    private boolean siendoAtacado = false;
    
    public Nino(int idNumerico, Sotano psotano, Bosque pbosque, Laboratorio plaboratorio, CentroComercial pcentrocomercial, 
    Alcantarillado palcantarillado, Portal pportalBosque, Portal pportalLab, Portal pportalCC, Portal pportalAlc) {
        // Esto es para que aparezcan asi N0001, N0023
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
    }
    public void run(){
        while(true){
            System.out.println("El nino " + idNino + " ha llegado al sotano");
            try{
                Thread.sleep(1000 + r.nextInt(1001));
            int portal = r.nextInt(4);
            switch(portal){
                case 0:
                    portalBosque.cruzarHaciaUpside(this);
                    bosque.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    bosque.salir(this);
                    portalBosque.cruzarHaciaHawkins(this);
                    break;
                case 1:
                    portalLab.cruzarHaciaUpside(this);
                    laboratorio.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    laboratorio.salir(this);
                    portalLab.cruzarHaciaHawkins(this);
                    break;
                case 2:
                    portalCC.cruzarHaciaUpside(this);
                    centrocomercial.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    centrocomercial.salir(this);
                    portalCC.cruzarHaciaHawkins(this);
                    break;
                case 3:
                    portalAlc.cruzarHaciaUpside(this);
                    alcantarillado.acceder(this);
                    Thread.sleep(3000 + r.nextInt(2001));
                    System.out.println("El nino " + idNino + " ha recogido 1 unidad de sangre de Vecna");
                    alcantarillado.salir(this);
                    portalAlc.cruzarHaciaHawkins(this);
                    break;
            }
            System.out.println("El nino " + idNino + " ha llegado a la RADIO WSQK");
            Thread.sleep(2000 + r.nextInt(2001));
            System.out.println("El nino " + idNino + " esta deambulando por la calle principal");
            Thread.sleep(3000 + r.nextInt(2001));
            }
            catch(InterruptedException e) {}
        }
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

    public String getIdNino() { return idNino; }
    public boolean isSiendoAtacado() { return siendoAtacado; }
    public void setSiendoAtacado(boolean b) { siendoAtacado = b; }
    public boolean isCapturado() { return capturado; }
    public void setCapturado(boolean b) { capturado = b; }
}