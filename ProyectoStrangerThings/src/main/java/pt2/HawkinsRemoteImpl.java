/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt2;

// HawkinsRemoteImpl.java
import interfaz.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import monitores.*;

//public class HawkinsRemoteImpl extends UnicastRemoteObject implements HawkinsRemote {
//
//    private final VentanaServidor ventana;        // Referencia a tu interfaz gráfica principal
//    private final EstadoGlobal estadoGlobal;
//    private final Colmena colmena;
//
//    // Referencias a todas las zonas (necesitarás pasarlas en el constructor)
//    private final Bosque bosque;
//    private final Laboratorio laboratorio;
//    private final CentroComercial centroComercial;
//    private final Alcantarillado alcantarillado;
//
//    private final Portal portalBosque, portalLab, portalCC, portalAlc;
//
//    private volatile boolean ejecutando = true;
//
//    public HawkinsRemoteImpl(VentanaServidor ventana, EstadoGlobal estadoGlobal, Colmena colmena,
//                             Bosque b, Laboratorio l, CentroComercial cc, Alcantarillado a,
//                             Portal p1, Portal p2, Portal p3, Portal p4) throws RemoteException {
//        
//        super();
//        this.ventana = ventana;
//        this.estadoGlobal = estadoGlobal;
//        this.colmena = colmena;
//        this.bosque = b;
//        this.laboratorio = l;
//        this.centroComercial = cc;
//        this.alcantarillado = a;
//        this.portalBosque = p1;
//        this.portalLab = p2;
//        this.portalCC = p3;
//        this.portalAlc = p4;
//    }
//
//    public int getTotalNinosEnHawkins() throws RemoteException {
//        return ventana.getTotalNinosEnHawkins();
//    }
//
//    public int getNinosEnPortal(int portalId) throws RemoteException {
//        switch (portalId) {
//            case 0: return portalBosque.getEsperandoParaIr();  // o el método que tengas
//            case 1: return portalLab.getEsperandoParaIr();
//            case 2: return portalCC.getEsperandoParaIr();
//            case 3: return portalAlc.getEsperandoParaIr();
//            default: return 0;
//        }
//    }
//
//    public String getEstadoPortales() throws RemoteException {
//        return "Portal Bosque: " + getNinosEnPortal(0) + " | Lab: " + getNinosEnPortal(1) +
//               " | CC: " + getNinosEnPortal(2) + " | Alcantarillado: " + getNinosEnPortal(3);
//    }
//
//    public int getNinosEnBosque() throws RemoteException {
//        return bosque.getNumeroNinos();
//    }
//
//    public int getNinosEnLaboratorio() throws RemoteException {
//        return laboratorio.getNumeroNinos();
//    }
//
//    public int getNinosEnCentroComercial() throws RemoteException {
//        return centroComercial.getNumeroNinos();
//    }
//
//    public int getNinosEnAlcantarillado() throws RemoteException {
//        return alcantarillado.getNumeroNinos();
//    }
//
//    public int getNinosEnColmena() throws RemoteException {
//        return colmena.getTotalNinos();
//    }
//
//    public int getDemogorgonsEnBosque() throws RemoteException {
//        return bosque.getNumeroDemogorgons();
//    }
//
//    public int getDemogorgonsEnLab() throws RemoteException {
//        return laboratorio.getNumeroDemogorgons();
//    }
//    
//    public int getDemogorgonsEnCC() throws RemoteException {
//        return centroComercial.getNumeroDemogorgons();
//    }
//    
//    public int getDemogorgonsEnAlcantarillado() throws RemoteException {
//        return alcantarillado.getNumeroDemogorgons();
//    }
//
//    public List<String> getRankingDemogorgons() throws RemoteException {
//        // Aquí necesitarás una forma de obtener el ranking desde todos los demogorgons
//        // Una buena solución es tener una lista compartida de Demogorgon o un gestor
//        return new ArrayList<>(); // Implementar después
//    }
//
//    public String getEstadoEventoGlobal() throws RemoteException {
//        return estadoGlobal.getDescripcionEventoConTiempo();
//    }
//
//    public void pausarEjecucion() throws RemoteException {
//        ejecutando = false;
//        // Aquí deberías pausar todos los hilos (niños y demogorgons)
//        System.out.println("Ejecución PAUSADA remotamente");
//    }
//
//    public void reanudarEjecucion() throws RemoteException {
//        ejecutando = true;
//        System.out.println("Ejecución REANUDADA remotamente");
//    }
//
//    public boolean isEjecutando() throws RemoteException {
//        return ejecutando;
//    }
//}
