package hilos;

import monitores.*;
import java.util.Random;

public class Creadorninos extends Thread {
    private final int TOTAL_NINOS = 1500;
    private Random random;
    
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
    private Log log;
    
    private ZonaHawkins callePrincipal;
    private ZonaHawkins radioWSQK;

    public Creadorninos(Sotano psotano, Bosque pbosque, Laboratorio plaboratorio, CentroComercial pcentrocomercial, Alcantarillado palcantarillado, Portal pportalBosque, Portal pportalLab, Portal pportalCC, Portal pportalAlc, EstadoGlobal e, Sangre san, Log log, ZonaHawkins cp, ZonaHawkins radio) {
        this.random = new Random();
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
        this.log = log;
        this.callePrincipal = cp;
        this.radioWSQK = radio;
    }

    @Override
    public void run() {
        System.out.println("Iniciando la generación de niños...");
        for (int i = 1; i <= TOTAL_NINOS; i++) {
            Nino nuevoNino = new Nino(i, sotano, bosque, laboratorio, centrocomercial, alcantarillado, portalBosque, portalLab, portalCC, portalAlc, estadoGlobal, sangre, callePrincipal, radioWSQK);
            nuevoNino.start();
            
            try {
                int tiempoEspera = 500 + random.nextInt(1501);
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("¡Se han generado los 1.500 niños!");
    }
}