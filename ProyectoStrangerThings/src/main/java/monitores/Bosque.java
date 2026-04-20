/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

/**
 *
 * @author marko
 */
public class Bosque {
    public synchronized void acceder(String id){
        try{
            Thread.sleep(1000);
            System.out.println("El nino " + id + " ha llegado al bosque");
        }
        catch(InterruptedException e) {}
    }
}
