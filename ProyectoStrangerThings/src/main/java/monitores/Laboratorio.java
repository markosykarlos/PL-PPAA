/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import hilos.*;
import java.util.ArrayList;

public class Laboratorio {
    private ArrayList<Nino> ninos = new ArrayList();
    public synchronized void acceder(Nino nino){
        System.out.println("El nino " + nino.getIdNino() + " ha llegado al laboratorio");
        ninos.add(nino);
    }
    
    public synchronized void salir(Nino nino){
        System.out.println("El nino " + nino.getIdNino() + " ha salido del laboratorio");
        ninos.remove(nino);
    }
}
