/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import hilos.*;

public class portal {

    private int capacidad;
    private int esperandoIda = 0;
    private int esperandoVuelta = 0;
    private int cruzando = 0;
    private boolean ocupado = false;

    public portal(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void cruzarHaciaUpside(ninos n) {
        try {
            esperandoIda++;
            System.out.println(n.getIdNino() + " esperando en portal (" + esperandoIda + "/" + capacidad + ")");
            while (esperandoIda < capacidad || esperandoVuelta > 0) {
                wait();
            }
            if (esperandoIda == capacidad) {
                cruzando = capacidad;
                esperandoIda = 0;
                notifyAll();
            }
            while (cruzando == 0 || ocupado) {
                wait();
            }
            ocupado = true;
            cruzando--;
            System.out.println(n.getIdNino() + " cruzando portal...");
            Thread.sleep(1000);
            ocupado = false;
            notifyAll();
    } catch (InterruptedException e) {}
}
    
    public synchronized void cruzarHaciaHawkins(ninos n) {
    try {
        esperandoVuelta++;
        System.out.println(n.getIdNino() + " quiere volver a Hawkins");
        while (ocupado) {
            wait();
        }
        ocupado = true;
        esperandoVuelta--;
        System.out.println(n.getIdNino() + " cruzando de vuelta...");
        Thread.sleep(1000);
        ocupado = false;
        notifyAll();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
}
