/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import hilos.*;
import java.util.ArrayList;

public class Bosque {
    private ArrayList<ninos> ninos = new ArrayList();
    public void acceder(ninos nino){
        System.out.println("El nino " + nino.getIdNino() + " ha llegado al bosque");
        ninos.add(nino);
    }
    public void salir(ninos nino){
        System.out.println("El nino " + nino.getIdNino() + " ha salido del bosque");
        ninos.remove(nino);
    }
}
