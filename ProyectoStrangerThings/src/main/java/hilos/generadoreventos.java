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
                estado.chequearPausa(); // Comprobamos si el RMI lo ha pausado
                
                // PARA PROBAR: Intervalo súper corto (5 a 10 segundos)
                int tiempoEspera = 5000 + random.nextInt(5000);
                System.out.println("[EVENTOS] Esperando " + (tiempoEspera/1000) + "s para el siguiente ataque...");
                Thread.sleep(tiempoEspera);

                estado.chequearPausa(); // Volvemos a comprobar por si lo pausaron mientras dormía

                int eventoElegido = 1 + random.nextInt(4);
                // Duración del evento: 10 a 15 segundos para que se vea bien en la interfaz
                int duracionEvento = 10000 + random.nextInt(5000);
                
                // Le pasamos la duración exacta a EstadoGlobal para sincronizar la interfaz
                estado.setEventoActivo(eventoElegido, duracionEvento); 

                String nombreEvento = "";
                switch (eventoElegido) {
                    case EstadoGlobal.APAGON_LABORATORIO: nombreEvento = "Apagón del Laboratorio"; break;
                    case EstadoGlobal.TORMENTA_UPSIDEDOWN: nombreEvento = "Tormenta del Upside Down"; break;
                    case EstadoGlobal.INTERVENCION_ELEVEN: nombreEvento = "Intervención de Eleven"; break;
                    case EstadoGlobal.RED_MENTAL: nombreEvento = "La Red Mental"; break;
                }

                System.out.println("!!! EVENTO INICIADO: " + nombreEvento + " (Durará " + (duracionEvento/1000) +"s) !!!");

                // Lógica de Eleven
                if (eventoElegido == EstadoGlobal.INTERVENCION_ELEVEN) {
                    int sangreDisponible = sangre.getCantidad();
                    sangre.usarSangre(sangreDisponible); 
                    List<Nino> rescatados = colmena.liberarNinos(sangreDisponible);
                    
                    System.out.println("Eleven ha gastado " + sangreDisponible + " unidades de sangre para rescatar a " + rescatados.size() + " niños.");
                    
                    for (Nino n : rescatados) {
                        n.serRescatado();
                    }
                }

                // El hilo duerme exactamente lo que dura el evento
                Thread.sleep(duracionEvento);

                System.out.println("--- Evento " + nombreEvento + " finalizado. Modo seguro. ---");
                estado.setEventoActivo(EstadoGlobal.NINGUNO, 0); // Apagamos el evento

            } catch (InterruptedException e) { 
                break; 
            }
        }
    }
}