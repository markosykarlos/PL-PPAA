package hilos;

import java.util.Random;
import monitores.*;

public class Demogorgon extends Thread {

    private String idDemogorgon;
    private int capturas;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centroComercial;
    private Alcantarillado alcantarillado;
    private Colmena colmena;
    private EstadoGlobal estadoGlobal;
    private Random r = new Random();
    private int zonaActual = -1;

    public Demogorgon(int idNumerico, Bosque b, Laboratorio l, CentroComercial c, Alcantarillado a, Colmena col, EstadoGlobal e) {
        this.idDemogorgon = String.format("D%04d", idNumerico); // [cite: 42]
        this.bosque = b;
        this.laboratorio = l;
        this.centroComercial = c;
        this.alcantarillado = a;
        this.colmena = col;
        this.estadoGlobal = e;
    }

    @Override
    public void run() {
        while (true) { // El comportamiento no finaliza nunca [cite: 101]
            try {
                // EVENTO: Intervención de Eleven (Parálisis) [cite: 94]
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.INTERVENCION_ELEVEN) {
                    Thread.sleep(500);
                    continue; 
                }

                int zonaDestino = decidirSiguienteZona();

                // EVENTO: Apagón del Laboratorio (No pueden cambiar de zona) [cite: 86]
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO && zonaActual != -1) {
                    zonaDestino = zonaActual; 
                }

                Nino presa = entrarYBuscarPresa(zonaDestino);
                zonaActual = zonaDestino;

                if (presa != null) {
                    int tiempoAtaque = 500 + r.nextInt(1001); // 0.5 - 1.5s [cite: 44]
                    
                    // EVENTO: Tormenta (Ataques más rápidos) [cite: 90]
                    if (estadoGlobal.getEventoActivo() == EstadoGlobal.TORMENTA_UPSIDEDOWN) tiempoAtaque /= 2;

                    Thread.sleep(tiempoAtaque);
                    
                    // 1/3 de ser capturado, 2/3 de resistir 
                    boolean exitoCaptura = (r.nextInt(3) == 0); 
                    resolverAtaqueEnZona(zonaActual, presa, exitoCaptura);

                    if (exitoCaptura) {
                        Thread.sleep(500 + r.nextInt(501)); // Depósito 0.5 - 1s [cite: 45]
                        // colmena.depositarNino(presa); 
                        capturas++;
                    }
                } else {
                    int tiempoEspera = 4000 + r.nextInt(1001); // 4-5s [cite: 48]
                    Thread.sleep(tiempoEspera);
                }
            } catch (InterruptedException e) { break; }
        }
    }

    private int decidirSiguienteZona() {
        if (estadoGlobal.getEventoActivo() == EstadoGlobal.RED_MENTAL) { // [cite: 97]
            int nB = bosque.getNumeroNinos();
            int nL = laboratorio.getNumeroNinos();
            int nC = centroComercial.getNumeroNinos();
            int nA = alcantarillado.getNumeroNinos();
            int max = Math.max(Math.max(nB, nL), Math.max(nC, nA));
            if (max == nB) return 0; if (max == nL) return 1; if (max == nC) return 2; return 3;
        }
        return r.nextInt(4); // [cite: 43]
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
}