package hilos;

import java.util.Random;
import monitores.*;

public class Demogorgon extends Thread {

    private String idDemogorgon;
    private int capturas;
    private Sotano sotano;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centroComercial;
    private Alcantarillado alcantarillado;
    private Colmena colmena;
    private EstadoGlobal estadoGlobal;
    private Random r = new Random();
    private int zonaActual = -1;

    public Demogorgon(int idNumerico, Sotano s, Bosque b, Laboratorio l, CentroComercial c, Alcantarillado a, Colmena col, EstadoGlobal e) {
        this.idDemogorgon = String.format("D%04d", idNumerico);
        this.sotano = s;
        this.bosque = b;
        this.laboratorio = l;
        this.centroComercial = c;
        this.alcantarillado = a;
        this.colmena = col;
        this.estadoGlobal = e;
    }

    @Override
    public void run() {
        while (true) { // El comportamiento no finaliza nunca
            try {
                // EVENTO: Intervención de Eleven (Parálisis)
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.INTERVENCION_ELEVEN) {
                    Thread.sleep(500);
                    continue; 
                }

                int zonaDestino = decidirSiguienteZona();

                // EVENTO: Apagón del Laboratorio (No pueden cambiar de zona)
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO && zonaActual != -1) {
                    zonaDestino = zonaActual; 
                }

                Nino presa = entrarYBuscarPresa(zonaDestino);
                zonaActual = zonaDestino;

                if (presa != null) {
                    int tiempoAtaque = 500 + r.nextInt(1001); // 0.5 - 1.5s
                    
                    // EVENTO: Tormenta (Ataques más rápidos)
                    if (estadoGlobal.getEventoActivo() == EstadoGlobal.TORMENTA_UPSIDEDOWN) tiempoAtaque /= 2;

                    boolean resiste = presa.serAtacado(tiempoAtaque);
                    boolean capturado = !resiste;

                    resolverAtaqueEnZona(zonaActual, presa, capturado);

                    if (capturado) {
                        Thread.sleep(500 + r.nextInt(501)); // Depósito 0.5 - 1s
                        colmena.depositarNino(presa); 
                        capturas++;
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
            if (max == nB) return 0; if (max == nL) return 1; if (max == nC) return 2; return 3;
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
}