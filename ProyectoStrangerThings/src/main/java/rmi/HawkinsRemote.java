
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HawkinsRemote extends Remote {

    int getTotalNinosEnHawkins() throws RemoteException;

    int getNinosEnPortal(int portalId) throws RemoteException;  // 0=Bosque, 1=Lab, 2=CC, 3=Alcantarillado
    String getEstadoPortales() throws RemoteException;

    int getNinosEnBosque() throws RemoteException;
    int getNinosEnLaboratorio() throws RemoteException;
    int getNinosEnCentroComercial() throws RemoteException;
    int getNinosEnAlcantarillado() throws RemoteException;
    int getNinosEnColmena() throws RemoteException;

    int getDemogorgonsEnBosque() throws RemoteException;
    int getDemogorgonsEnLaboratorio() throws RemoteException;
    int getDemogorgonsEnCentroComercial() throws RemoteException;
    int getDemogorgonsEnAlcantarillado() throws RemoteException;

    List<String> getRankingDemogorgons() throws RemoteException;

    String getEstadoEventoGlobal() throws RemoteException;

    void pausarEjecucion() throws RemoteException;
    void reanudarEjecucion() throws RemoteException;
    boolean isEjecutando() throws RemoteException;
    String getIDsDemogorgonsEnBosque() throws RemoteException;
    String getIDsDemogorgonsEnLaboratorio() throws RemoteException;
    String getIDsDemogorgonsEnCentroComercial() throws RemoteException;
    String getIDsDemogorgonsEnAlcantarillado() throws RemoteException;
}
