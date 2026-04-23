/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import java.util.Random;

import hilos.*;

public class Sotano {
    private Random r = new Random();
    public void acceder(Nino nino){
        try{
            Thread.sleep(1000 + r.nextInt(1001));
            System.out.println("El nino " + nino.getIdNino() + " ha llegado al sotano");
        }
        catch(InterruptedException e) {}
    }
}
