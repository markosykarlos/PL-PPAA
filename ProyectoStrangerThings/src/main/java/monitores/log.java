package monitores;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private String rutaArchivo = "hawkins.txt"; 
    private DateTimeFormatter formatoFecha;

    public Log() {
        this.formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    // El método es 'synchronized' 
    public synchronized void escribir(String mensaje) {
        String fechaHora = LocalDateTime.now().format(formatoFecha);
        String lineaLog = "[" + fechaHora + "] " + mensaje;

        // Imprimimos por consola para verlo nosotros en NetBeans
        System.out.println(lineaLog);

        // Y lo guardamos en el archivo (el true es para añadir, no sobreescribir)
        try (PrintWriter out = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            out.println(lineaLog);
        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}

/*¿Cómo lo integras en lo que ya tienes?
En tu MainServidor, instancias el Log una sola vez y se lo pasas a los hilos cuando los creas:


Log logger = new Log();
GeneradorEventos generador = new GeneradorEventos(estado, logger); 
Tendrías que actualizar el constructor

Y luego, dentro del hilo (por ejemplo, en el Nino o el GeneradorEventos), en lugar de usar System.out.println(...), llamas a tu nuevo monitor:
logger.escribir("EVENTO GLOBAL: Apagón del Laboratorio iniciado"); */