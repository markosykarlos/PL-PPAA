/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;

// HawkinsRemote.java
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HawkinsRemote extends Remote {

    // Información general de Hawkins
    int getTotalNinosEnHawkins() throws RemoteException;
    
    // Portales
    int getNinosEnPortal(int portalId) throws RemoteException;  // 0=Bosque, 1=Lab, 2=CC, 3=Alcantarillado
    String getEstadoPortales() throws RemoteException;

    // Upside Down - Niños
    int getNinosEnBosque() throws RemoteException;
    int getNinosEnLaboratorio() throws RemoteException;
    int getNinosEnCentroComercial() throws RemoteException;
    int getNinosEnAlcantarillado() throws RemoteException;
    int getNinosEnColmena() throws RemoteException;

    // Demogorgons
    int getDemogorgonsEnBosque() throws RemoteException;
    int getDemogorgonsEnLaboratorio() throws RemoteException;
    int getDemogorgonsEnCentroComercial() throws RemoteException;
    int getDemogorgonsEnAlcantarillado() throws RemoteException;

    // Ranking
    List<String> getRankingDemogorgons() throws RemoteException;   // Ej: "D0001 (45 capturas)"

    // Evento Global
    String getEstadoEventoGlobal() throws RemoteException;   // "INTERVENCIÓN DE ELEVEN - 7s restantes"

    // Control del servidor
    void pausarEjecucion() throws RemoteException;
    void reanudarEjecucion() throws RemoteException;
    boolean isEjecutando() throws RemoteException;
}
