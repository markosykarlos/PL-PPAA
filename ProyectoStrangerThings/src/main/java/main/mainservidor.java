package main;

import hilos.Creadorninos;

public class mainservidor {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO LA BATALLA DE HAWKINS ---");

        // Creamos y arrancamos el hilo generador
        Creadorninos generador = new Creadorninos();
        generador.start();

        // TODO: Instanciar las zonas compartidas (Hawkins, Portales, Upside Down)
        // TODO: Instanciar y arrancar el Demogorgon Alpha (D0000)
    }
}