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

    public synchronized void escribir(String mensaje) {
        String fechaHora = LocalDateTime.now().format(formatoFecha);
        String lineaLog = "[" + fechaHora + "] " + mensaje;
        
        // Imprimimos por consola para verlo también en NetBeans
        System.out.println(lineaLog);
        
        // Guardamos en el archivo (el 'true' es para añadir, no sobreescribir)
        try (PrintWriter out = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            out.println(lineaLog);
        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}