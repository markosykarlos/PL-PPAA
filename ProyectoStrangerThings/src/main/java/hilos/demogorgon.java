package hilos;

import java.util.Random;
import monitores.*;

public class Demogorgon extends Thread {

    private String idDemogorgon;
    private int capturas;
    
    // Monitores de las zonas
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centroComercial;
    private Alcantarillado alcantarillado;
    
    // Otros monitores
    private Colmena colmena;
    private EstadoGlobal estadoGlobal;
    
    private Random r = new Random();
    private int zonaActual = -1; // -1 indica que aún no ha entrado a ninguna zona

    public Demogorgon(int idNumerico, Bosque bosque, Laboratorio laboratorio, CentroComercial centroComercial, Alcantarillado alcantarillado, Colmena colmena, EstadoGlobal estadoGlobal) {
        // Formato DXXXX [cite: 42]
        this.idDemogorgon = String.format("D%04d", idNumerico);
        this.capturas = 0;
        this.bosque = bosque;
        this.laboratorio = laboratorio;
        this.centroComercial = centroComercial;
        this.alcantarillado = alcantarillado;
        this.colmena = colmena;
        this.estadoGlobal = estadoGlobal;
    }

    @Override
    public void run() {
        System.out.println("El Demogorgon " + idDemogorgon + " ha sido engendrado por Vecna.");

        // El comportamiento de los demogorgons no finaliza nunca [cite: 49, 101]
        while (true) {
            try {
                // 1. EVENTO: Comprobar si Eleven los tiene paralizados 
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.INTERVENCION_ELEVEN) {
                    Thread.sleep(500); // Espera activa controlada
                    continue; 
                }

                // 2. Determinar la siguiente zona
                int zonaDestino = decidirSiguienteZona();

                // 3. EVENTO: Comprobar Apagón del Laboratorio (no pueden cambiar de zona) 
                if (estadoGlobal.getEventoActivo() == EstadoGlobal.APAGON_LABORATORIO && zonaActual != -1) {
                    zonaDestino = zonaActual; 
                }

                // 4. Buscar presa en la zona elegida [cite: 43, 44]
                Nino presa = entrarYBuscarPresa(zonaDestino);
                zonaActual = zonaDestino;

                if (presa != null) {
                    // HAY UN NIÑO: Proceder al ataque [cite: 44]
                    int tiempoAtaque = 500 + r.nextInt(1001); // Entre 0.5 y 1.5 segundos [cite: 44]
                    System.out.println("El demogorgon " + idDemogorgon + " ataca al niño " + presa.getIdNino());
                    
                    Thread.sleep(tiempoAtaque); // El demogorgon emplea tiempo en el ataque [cite: 44]
                    
                    // Cálculo de éxito: 2/3 de resistir (falla el ataque), 1/3 de ser capturado [cite: 67]
                    boolean exitoCaptura = (r.nextInt(3) == 0); 

                    // Le comunicamos al monitor de la zona cómo terminó el ataque para que libere al niño
                    resolverAtaqueEnZona(zonaActual, presa, exitoCaptura);

                    if (exitoCaptura) {
                        // ATAQUE CON ÉXITO: El niño es trasladado a la COLMENA [cite: 45]
                        int tiempoDeposito = 500 + r.nextInt(501); // Entre 0.5 y 1 segundo [cite: 45]
                        Thread.sleep(tiempoDeposito);
                        
                        // DEBES CREAR ESTE MÉTODO EN COLMENA
                        // colmena.depositarNino(presa); 
                        
                        capturas++; 
                        System.out.println("El niño " + presa.getIdNino() + " ha sido capturado por " + idDemogorgon + " (Capturas: " + capturas + ")");
                    } else {
                        // ATAQUE FRACASA: El niño permanece en el área (el monitor ya lo ha liberado) [cite: 46]
                        System.out.println("El niño " + presa.getIdNino() + " resistió el ataque de " + idDemogorgon);
                    }
                } else {
                    // NO HAY NIÑOS: Permanecer en el área entre 4 y 5 segundos [cite: 48]
                    int tiempoEspera = 4000 + r.nextInt(1001); 
                    
                    // EVENTO: Si hay Tormenta, el tiempo entre ataques se reduce a la mitad 
                    if (estadoGlobal.getEventoActivo() == EstadoGlobal.TORMENTA_UPSIDEDOWN) {
                        tiempoEspera /= 2; 
                    }
                    
                    Thread.sleep(tiempoEspera);
                }

                // 5. Tras el ataque (o la espera), salir de la zona para ir a otra [cite: 47, 48]
                salirDeZona(zonaActual);

            } catch (InterruptedException e) {
                System.err.println("Hilo Demogorgon " + idDemogorgon + " interrumpido.");
                break;
            }
        }
    }

    /**
     * Decide la siguiente zona, evaluando si está activa La Red Mental.
     */
    private int decidirSiguienteZona() {
        // EVENTO: La Red Mental (van a la zona con más niños) 
        if (estadoGlobal.getEventoActivo() == EstadoGlobal.RED_MENTAL) {
            // Nota: Debes crear el método getNumeroNinos() en los monitores de las zonas
            int nBosque = 0; // bosque.getNumeroNinos();
            int nLab = 0; // laboratorio.getNumeroNinos();
            int nCC = 0; // centroComercial.getNumeroNinos();
            int nAlc = 0; // alcantarillado.getNumeroNinos();
            
            int max = Math.max(Math.max(nBosque, nLab), Math.max(nCC, nAlc));
            
            if (max == nBosque) return 0;
            if (max == nLab) return 1;
            if (max == nCC) return 2;
            return 3;
        } else {
            // Movimiento aleatorio normal: 0=Bosque, 1=Lab, 2=CC, 3=Alc [cite: 43]
            return r.nextInt(4);
        }
    }

    /**
     * Accede al monitor correspondiente para buscar una presa.
     */
    private Nino entrarYBuscarPresa(int zona) {
        switch (zona) {
            // case 0: return bosque.buscarPresa();
            // case 1: return laboratorio.buscarPresa();
            // case 2: return centroComercial.buscarPresa();
            // case 3: return alcantarillado.buscarPresa();
            default: return null;
        }
    }

    /**
     * Avisa al monitor de la zona cómo terminó el ataque para despertar al niño.
     */
    private void resolverAtaqueEnZona(int zona, Nino presa, boolean capturado) {
        switch (zona) {
            // case 0: bosque.resolverAtaque(presa, capturado); break;
            // case 1: laboratorio.resolverAtaque(presa, capturado); break;
            // case 2: centroComercial.resolverAtaque(presa, capturado); break;
            // case 3: alcantarillado.resolverAtaque(presa, capturado); break;
        }
    }

    /**
     * Registra la salida del Demogorgon del monitor de la zona.
     */
    private void salirDeZona(int zona) {
        // switch (zona) {
        //     case 0: bosque.salirDemogorgon(); break;
        //     case 1: laboratorio.salirDemogorgon(); break;
        //     case 2: centroComercial.salirDemogorgon(); break;
        //     case 3: alcantarillado.salirDemogorgon(); break;
        // }
    }
    
    public String getIdDemogorgon() {
        return idDemogorgon;
    }
    
    public int getCapturas() {
        return capturas;
    }
}