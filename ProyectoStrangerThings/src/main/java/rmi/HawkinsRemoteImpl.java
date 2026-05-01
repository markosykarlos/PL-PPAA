
package rmi;

import interfaz.VentanaServidor;
import monitores.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class HawkinsRemoteImpl extends UnicastRemoteObject implements HawkinsRemote {

    private final VentanaServidor ventana;
    private final EstadoGlobal estadoGlobal;
    private final Colmena colmena;

    private final Bosque bosque;
    private final Laboratorio laboratorio;
    private final CentroComercial centroComercial;
    private final Alcantarillado alcantarillado;

    private final Portal portalBosque, portalLab, portalCC, portalAlc;

    private volatile boolean ejecutando = true;

    public HawkinsRemoteImpl(VentanaServidor ventana, EstadoGlobal estadoGlobal, Colmena colmena,
                             Bosque b, Laboratorio l, CentroComercial cc, Alcantarillado a,
                             Portal p1, Portal p2, Portal p3, Portal p4) throws RemoteException {

        super();
        this.ventana = ventana;
        this.estadoGlobal = estadoGlobal;
        this.colmena = colmena;
        this.bosque = b;
        this.laboratorio = l;
        this.centroComercial = cc;
        this.alcantarillado = a;
        this.portalBosque = p1;
        this.portalLab = p2;
        this.portalCC = p3;
        this.portalAlc = p4;
    }

    @Override
    public int getTotalNinosEnHawkins() throws RemoteException {
        return ventana.getTotalNinosEnHawkins();
    }

    // ==================== PORTALES ====================
    @Override
    public int getNinosEnPortal(int portalId) throws RemoteException {
        switch (portalId) {
            case 0: return portalBosque.getNumeroNinosEsperando();
            case 1: return portalLab.getNumeroNinosEsperando();
            case 2: return portalCC.getNumeroNinosEsperando();
            case 3: return portalAlc.getNumeroNinosEsperando();
            default: return 0;
        }
    }

    @Override
    public String getEstadoPortales() throws RemoteException {
        return "Portal Bosque: " + getNinosEnPortal(0) +
               " | Lab: " + getNinosEnPortal(1) +
               " | CC: " + getNinosEnPortal(2) +
               " | Alcantarillado: " + getNinosEnPortal(3);
    }

    // ==================== NIÑOS EN UPSIDE DOWN ====================
    @Override
    public int getNinosEnBosque() throws RemoteException {
        return bosque.getNumeroNinos();
    }

    @Override
    public int getNinosEnLaboratorio() throws RemoteException {
        return laboratorio.getNumeroNinos();
    }

    @Override
    public int getNinosEnCentroComercial() throws RemoteException {
        return centroComercial.getNumeroNinos();
    }

    @Override
    public int getNinosEnAlcantarillado() throws RemoteException {
        return alcantarillado.getNumeroNinos();
    }

    @Override
    public int getNinosEnColmena() throws RemoteException {
        return colmena.getTotalNinos();
    }

    // ==================== DEMOGORGONS ====================
    @Override
    public int getDemogorgonsEnBosque() throws RemoteException {
        return bosque.getNumeroDemogorgons();
    }

    @Override
    public int getDemogorgonsEnLaboratorio() throws RemoteException {
        return laboratorio.getNumeroDemogorgons();
    }

    @Override
    public int getDemogorgonsEnCentroComercial() throws RemoteException {
        return centroComercial.getNumeroDemogorgons();
    }

    @Override
    public int getDemogorgonsEnAlcantarillado() throws RemoteException {
        return alcantarillado.getNumeroDemogorgons();
    }

    // ==================== RANKING  ====================
 @Override
    public List<String> getRankingDemogorgons() throws RemoteException {
        
        return estadoGlobal.obtenerTopDemogorgons();
    }

    // ==================== EVENTO GLOBAL ====================
    @Override
    public String getEstadoEventoGlobal() throws RemoteException {
        return estadoGlobal.getDescripcionEventoConTiempo();
    }

    // ==================== CONTROL DE EJECUCIÓN ====================
@Override
    public void pausarEjecucion() throws RemoteException {
        ejecutando = false;
        estadoGlobal.pausar(); // <--- AÑADIR ESTO
        System.out.println("[RMI] Sistema PAUSADO remotamente");
    }

    @Override
    public void reanudarEjecucion() throws RemoteException {
        ejecutando = true;
        estadoGlobal.reanudar(); // <--- AÑADIR ESTO
        System.out.println("[RMI] Sistema REANUDADO remotamente");
    }

    @Override
    public boolean isEjecutando() throws RemoteException {
        return ejecutando;
    }
}