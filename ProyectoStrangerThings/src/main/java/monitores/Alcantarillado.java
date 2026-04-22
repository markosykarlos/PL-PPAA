/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import hilos.*;
import java.util.ArrayList;

public class Alcantarillado {
    private ArrayList<ninos> ninos = new ArrayList();
    public synchronized void acceder(ninos nino){
        System.out.println("El nino " + nino.getIdNino() + " ha llegado al alcantarillado");
        ninos.add(nino);
    }
    
    public synchronized void salir(ninos nino){
        System.out.println("El nino " + nino.getIdNino() + " ha salido del alcantarillado");
        ninos.remove(nino);
    }
}
