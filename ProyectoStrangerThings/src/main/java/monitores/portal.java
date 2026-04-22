/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitores;

import hilos.*;

public class portal {

    private int capacidad;
    private int esperando = 0;
    private int cruzando = 0; // cuántos quedan por cruzar del grupo

    public portal(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void cruzarHaciaUpside(ninos n) {
        try {
            // 1. Llega al portal
            esperando++;
            System.out.println(n.getIdNino() + " esperando en portal (" + esperando + "/" + capacidad + ")");

            // 2. Esperar a formar grupo
            while (esperando < capacidad) {
                wait();
            }

            // 3. El último que llega activa el grupo
            if (esperando == capacidad) {
                cruzando = capacidad; // se habilita el grupo
                esperando = 0;        // reset para el siguiente grupo
                notifyAll();          // despierta a todos
            }

            // 4. Paso de uno en uno
            while (cruzando == 0) {
                wait();
            }

            cruzando--; // este niño está cruzando

            System.out.println(n.getIdNino() + " cruzando portal...");
            Thread.sleep(1000); // tiempo de cruce

            // 5. Avisar al siguiente
            notifyAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
