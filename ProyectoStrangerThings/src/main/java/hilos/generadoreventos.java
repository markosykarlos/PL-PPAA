package hilos;

import monitores.*;
import java.util.Random;
import java.util.List;

public class Generadoreventos extends Thread {
    private EstadoGlobal estado;
    private Colmena colmena;
    private Sangre sangre;
    private Random random;

    public Generadoreventos(EstadoGlobal estado, Colmena colmena, Sangre sangre) {
        this.estado = estado;
        this.colmena = colmena;
        this.sangre = sangre;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("Generador de eventos globales activado.");
        while (true) {
            try {
                estado.chequearPausa();
                int tiempoEspera = 30000 + random.nextInt(30001);
                System.out.println("[EVENTOS] Esperando " + (tiempoEspera/1000) + "s para el siguiente");
                Thread.sleep(tiempoEspera);
                estado.chequearPausa();
                int eventoElegido = 1 + random.nextInt(4);
                int duracionEvento = 10000 + random.nextInt(5000);
                estado.setEventoActivo(eventoElegido, duracionEvento); 
                String nombreEvento = "";
                switch (eventoElegido) {
                    case EstadoGlobal.APAGON_LABORATORIO: nombreEvento = "Apagon del Laboratorio"; break;
                    case EstadoGlobal.TORMENTA_UPSIDEDOWN: nombreEvento = "Tormenta del Upside Down"; break;
                    case EstadoGlobal.INTERVENCION_ELEVEN: nombreEvento = "Intervencion de Eleven"; break;
                    case EstadoGlobal.RED_MENTAL: nombreEvento = "La Red Mental"; break;
                }
                System.out.println("EVENTO INICIADO: " + nombreEvento + " (va a durar " + (duracionEvento/1000) +"s)");
                if (eventoElegido == EstadoGlobal.INTERVENCION_ELEVEN) {
                    int sangreDisponible = sangre.getCantidad();
                    sangre.usarSangre(sangreDisponible); 
                    List<Nino> rescatados = colmena.liberarNinos(sangreDisponible);
                    System.out.println("Eleven ha gastado " + sangreDisponible + " unidades de sangre para rescatar a " + rescatados.size() + " ninos.");
                    for (Nino n : rescatados) {
                        n.serRescatado();
                    }
                }
                Thread.sleep(duracionEvento);
                System.out.println("Evento " + nombreEvento + " finalizado");
                estado.setEventoActivo(EstadoGlobal.NINGUNO, 0); // Apagamos el evento
            } catch (InterruptedException e) { 
                break; 
            }
        }
    }
}