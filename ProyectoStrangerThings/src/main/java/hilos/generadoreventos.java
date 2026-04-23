package hilos;

import monitores.EstadoGlobal;
import java.util.Random;

public class Generadoreventos extends Thread {
    private EstadoGlobal estado;
    private Random random;

    public Generadoreventos(EstadoGlobal estado) {
        this.estado = estado;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("Generador de eventos globales activado.");

        while (true) {
            try {
                
                int tiempoEspera = 30000 + random.nextInt(30001);
                Thread.sleep(tiempoEspera);

              
                int eventoElegido = 1 + random.nextInt(4);

                // Activar el evento en el monitor
                estado.setEventoActivo(eventoElegido);

                String nombreEvento = "";
                switch (eventoElegido) {
                    case EstadoGlobal.APAGON_LABORATORIO:
                        nombreEvento = "Apagón del Laboratorio";
                        break;
                    case EstadoGlobal.TORMENTA_UPSIDEDOWN:
                        nombreEvento = "Tormenta del Upside Down";
                        break;
                    case EstadoGlobal.INTERVENCION_ELEVEN:
                        nombreEvento = "Intervención de Eleven";
                        break;
                    case EstadoGlobal.RED_MENTAL:
                        nombreEvento = "La Red Mental";
                        break;
                }
                
                System.out.println("!!! EVENTO GLOBAL INICIADO: " + nombreEvento + " !!!");

                // Duración del evento: entre 5 y 10 segundos
                int duracionEvento = 5000 + random.nextInt(5001);
                Thread.sleep(duracionEvento);

                //  Finalizar el evento volviendo a ponerlo a NINGUNO (0)
                System.out.println("--- Evento " + nombreEvento + " finalizado. ---");
                estado.setEventoActivo(EstadoGlobal.NINGUNO);

            } catch (InterruptedException e) {
                System.err.println("Generador de eventos interrumpido.");
                break;
            }
        }
    }
}