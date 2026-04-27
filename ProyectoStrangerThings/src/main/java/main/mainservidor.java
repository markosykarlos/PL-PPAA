package main;

import hilos.*;
import monitores.*;
import interfaz.VentanaServidor;

public class Mainservidor {
    public static int contadorDemogorgons = 1;

    public static void main(String[] args) {
        EstadoGlobal estadoGlobal = new EstadoGlobal();
        Log log = new Log();
        Sangre sangre = new Sangre();
        Colmena colmena = new Colmena();
        
        Sotano sotano = new Sotano();
        ZonaHawkins callePrincipal = new ZonaHawkins();
        ZonaHawkins radioWSQK = new ZonaHawkins();
        
        Portal portalBosque = new Portal(2, estadoGlobal);
        Portal portalLab = new Portal(3, estadoGlobal);
        Portal portalCC = new Portal(4, estadoGlobal);
        Portal portalAlc = new Portal(2, estadoGlobal);
        
        Bosque bosque = new Bosque();
        Laboratorio laboratorio = new Laboratorio();
        CentroComercial centrocomercial = new CentroComercial();
        Alcantarillado alcantarillado = new Alcantarillado();

        // Le pasamos TODOS los monitores a la ventana
        VentanaServidor ventana = new VentanaServidor(sangre, colmena, bosque, laboratorio, centrocomercial, alcantarillado, estadoGlobal, sotano, callePrincipal, radioWSQK, portalBosque, portalLab, portalCC, portalAlc);
        ventana.setVisible(true);

        System.out.println("--- INICIANDO LA BATALLA DE HAWKINS ---");

        Generadoreventos generadorEventos = new Generadoreventos(estadoGlobal, colmena, sangre);
        generadorEventos.start();

        Demogorgon alpha = new Demogorgon(0, sotano, bosque, laboratorio, centrocomercial, alcantarillado, colmena, estadoGlobal, sangre);
        alpha.start();

        // Al generador de niños ya no le instanciamos los portales dentro, se los pasamos hechos
       Creadorninos generadorNinos = new Creadorninos(sotano, bosque, laboratorio, centrocomercial, alcantarillado, portalBosque, portalLab, portalCC, portalAlc, estadoGlobal, sangre, log, callePrincipal, radioWSQK);
        generadorNinos.start();
    }
}