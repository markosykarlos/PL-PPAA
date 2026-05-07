
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HawkinsRemote extends Remote {

    // Hawkins
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
    List<String> getRankingDemogorgons() throws RemoteException;

    // Evento Global
    String getEstadoEventoGlobal() throws RemoteException;

    // Control del servidor
    void pausarEjecucion() throws RemoteException;
    void reanudarEjecucion() throws RemoteException;
    boolean isEjecutando() throws RemoteException;
    String getIDsDemogorgonsEnBosque() throws RemoteException;
    String getIDsDemogorgonsEnLaboratorio() throws RemoteException;
    String getIDsDemogorgonsEnCentroComercial() throws RemoteException;
    String getIDsDemogorgonsEnAlcantarillado() throws RemoteException;
}
