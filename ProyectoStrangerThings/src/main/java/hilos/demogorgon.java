package hilos;

import monitores.*;
import java.util.Random;
import main.Mainservidor;

public class Demogorgon extends Thread {
    private String idDemogorgon;
    private int capturas = 0;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centroComercial;
    private Alcantarillado alcantarillado;
    private Colmena colmena;
    private EstadoGlobal estadoGlobal;
    private Sangre sangre;
    private Random r = new Random();
    private int zonaActual = -1;

    public Demogorgon(int idNumerico, Sotano s, Bosque b, Laboratorio l, CentroComercial c, Alcantarillado a, Colmena col, EstadoGlobal e, Sangre san) {
        this.idDemogorgon = String.format("D%04d", idNumerico);
        this.sotano = s;
        this.bosque = b;
        this.laboratorio = l;
        this.centroComercial = c;
        this.alcantarillado = a;
        this.colmena = col;
        this.estadoGlobal = e;
        this.sangre = san;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.INTERVENCION_ELEVEN) {
                    Thread.sleep(500);
                    continue;
                }

                int zonaDestino = decidirSiguienteZona();
                
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO && zonaActual != -1) {
                    zonaDestino = zonaActual;
                }

                Nino presa = entrarYBuscarPresa(zonaDestino);
                if (zonaActual != -1) salirDeZona(zonaActual, idDemogorgon);
                entrarEnZona(zonaDestino, idDemogorgon);
                zonaActual = zonaDestino;

                if (presa != null) {
                    int tiempoAtaque = 500 + r.nextInt(1001);
                    if (estadoGlobal.getEventoActivo() == EstadoGlobal.TORMENTA_UPSIDEDOWN) {
                        tiempoAtaque /= 2;
                    }

                    boolean resiste = presa.serAtacado(tiempoAtaque);
                    boolean capturado = !resiste;
                    
                    resolverAtaqueEnZona(zonaActual, presa, capturado);

                    if (capturado) {
                        Thread.sleep(500 + r.nextInt(501)); 
                        colmena.depositarNino(presa);
                        capturas++;
                        System.out.println(idDemogorgon + " ha capturado al niño " + presa.getIdNino() + " (Capturas: " + capturas + ")");
                        
                        // Engendrar nuevo Demogorgon cada 8 capturas
                        if (capturas % 8 == 0) {
                            int nuevoId = Mainservidor.contadorDemogorgons++;
                            Demogorgon nuevoDemo = new Demogorgon(nuevoId, sotano, bosque, laboratorio, centroComercial, alcantarillado, colmena, estadoGlobal, sangre);
                            nuevoDemo.start();
                            System.out.println("!!! Vecna ha engendrado un nuevo Demogorgon: " + nuevoDemo.idDemogorgon + " !!!");
                        }
                    }
                } else {
                    int tiempoEspera = 4000 + r.nextInt(1001);
                    Thread.sleep(tiempoEspera);
                }
            } catch (InterruptedException e) { break; }
        }
    }

    private int decidirSiguienteZona() {
        if (estadoGlobal.getEventoActivo() == EstadoGlobal.RED_MENTAL) {
            int nB = bosque.getNumeroNinos();
            int nL = laboratorio.getNumeroNinos();
            int nC = centroComercial.getNumeroNinos();
            int nA = alcantarillado.getNumeroNinos();
            int max = Math.max(Math.max(nB, nL), Math.max(nC, nA));
            if (max == nB) return 0; 
            if (max == nL) return 1; 
            if (max == nC) return 2; 
            return 3;
        }
        return r.nextInt(4);
    }

    private Nino entrarYBuscarPresa(int zona) {
        switch (zona) {
            case 0: return bosque.buscarPresa();
            case 1: return laboratorio.buscarPresa();
            case 2: return centroComercial.buscarPresa();
            case 3: return alcantarillado.buscarPresa();
            default: return null;
        }
    }

    private void resolverAtaqueEnZona(int zona, Nino p, boolean c) {
        switch (zona) {
            case 0: bosque.resolverAtaque(p, c); break;
            case 1: laboratorio.resolverAtaque(p, c); break;
            case 2: centroComercial.resolverAtaque(p, c); break;
            case 3: alcantarillado.resolverAtaque(p, c); break;
        }
    }
    private void entrarEnZona(int zona, String id) {
        switch(zona) {
            case 0: bosque.accederDemogorgon(id); break;
            case 1: laboratorio.accederDemogorgon(id); break;
            case 2: centroComercial.accederDemogorgon(id); break;
            case 3: alcantarillado.accederDemogorgon(id); break;
        }
    }
    private void salirDeZona(int zona, String id) {
        switch(zona) {
            case 0: bosque.salirDemogorgon(id); break;
            case 1: laboratorio.salirDemogorgon(id); break;
            case 2: centroComercial.salirDemogorgon(id); break;
            case 3: alcantarillado.salirDemogorgon(id); break;
        }
    }
}