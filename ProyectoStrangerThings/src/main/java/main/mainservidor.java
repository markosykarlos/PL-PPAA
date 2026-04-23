package main;

import hilos.Creadorninos;
import monitores.*;
import interfaz.VentanaServidor; // Importamos la ventana

public class Mainservidor {
    public static void main(String[] args) {
        
        // 1. Mostrar la ventana lo primero de todo
        VentanaServidor ventana = new VentanaServidor();
        ventana.setVisible(true);
        
        // 2. Instanciar los monitores que ya tienes
        Sotano sotano = new Sotano();
        Bosque bosque = new Bosque();
        Laboratorio laboratorio = new Laboratorio();
        CentroComercial centrocomercial = new CentroComercial();
        Alcantarillado alcantarillado = new Alcantarillado();
        
        System.out.println("--- INICIANDO LA BATALLA DE HAWKINS ---");

        // Creamos y arrancamos el hilo generador
        Creadorninos generador = new Creadorninos(sotano, bosque, laboratorio, centrocomercial, alcantarillado);
        generador.start();

        // TODO: Instanciar el resto de cosas cuando las vayamos creando
    }
}