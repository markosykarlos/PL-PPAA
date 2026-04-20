package main;

import hilos.Creadorninos;
import monitores.*;

public class mainservidor {
    public static void main(String[] args) {
        Sotano sotano = new Sotano();
        Bosque bosque = new Bosque();
        Laboratorio laboratorio = new Laboratorio();
        CentroComercial centrocomercial = new CentroComercial();
        Alcantarillado alcantarillado = new Alcantarillado();
        System.out.println("--- INICIANDO LA BATALLA DE HAWKINS ---");

        // Creamos y arrancamos el hilo generador
        Creadorninos generador = new Creadorninos(sotano, bosque, laboratorio, centrocomercial, alcantarillado);
        generador.start();

        // TODO: Instanciar las zonas compartidas (Hawkins, Portales, Upside Down)
        // TODO: Instanciar y arrancar el Demogorgon Alpha (D0000)
    }
}