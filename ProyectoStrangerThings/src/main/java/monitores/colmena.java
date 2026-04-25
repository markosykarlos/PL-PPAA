/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import hilos.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LUGIA
 */
public class Colmena {
    private List<Nino> ninosCapturados = new ArrayList<>();

    // Depositar un niño capturado
    public synchronized void depositarNino(Nino n) {
        ninosCapturados.add(n);
        System.out.println("El nino " + n.getIdNino() + " ha sido llevado a la colmena.");
        System.out.println("Total de ninos en la colmena: " + ninosCapturados.size());
    }

    // Obtener número de niños capturados
    public synchronized int getTotalNinos() {
        return ninosCapturados.size();
    }
    // Mostrar todos
    public synchronized void mostrarNinos() {
        System.out.println("Ninos en la colmena:");
        for (Nino n : ninosCapturados) {
            System.out.println(n.getIdNino());
        }
    }
}
