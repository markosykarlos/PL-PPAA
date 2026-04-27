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
                // Intervalo entre eventos: 30 a 60 segundos
                int tiempoEspera = 30000 + random.nextInt(30001);
                Thread.sleep(tiempoEspera);

                int eventoElegido = 1 + random.nextInt(4);
                estado.setEventoActivo(eventoElegido);

                String nombreEvento = "";
                switch (eventoElegido) {
                    case EstadoGlobal.APAGON_LABORATORIO: nombreEvento = "Apagón del Laboratorio"; break;
                    case EstadoGlobal.TORMENTA_UPSIDEDOWN: nombreEvento = "Tormenta del Upside Down"; break;
                    case EstadoGlobal.INTERVENCION_ELEVEN: nombreEvento = "Intervención de Eleven"; break;
                    case EstadoGlobal.RED_MENTAL: nombreEvento = "La Red Mental"; break;
                }

                System.out.println("!!! EVENTO GLOBAL INICIADO: " + nombreEvento + " !!!");

                // Lógica especial de Eleven: Rescatar niños a cambio de sangre
                if (eventoElegido == EstadoGlobal.INTERVENCION_ELEVEN) {
                    int sangreDisponible = sangre.getCantidad();
                    sangre.usarSangre(sangreDisponible); // Se gasta toda
                    List<Nino> rescatados = colmena.liberarNinos(sangreDisponible);
                    
                    System.out.println("Eleven ha gastado " + sangreDisponible + " unidades de sangre para rescatar a " + rescatados.size() + " niños.");
                    
                    for (Nino n : rescatados) {
                        n.serRescatado(); // Despierta al hilo del niño
                    }
                }

                // Duración del evento: 5 a 10 segundos
                int duracionEvento = 5000 + random.nextInt(5001);
                Thread.sleep(duracionEvento);

                System.out.println("--- Evento " + nombreEvento + " finalizado. ---");
                estado.setEventoActivo(EstadoGlobal.NINGUNO);

            } catch (InterruptedException e) { break; }
        }
    }
}