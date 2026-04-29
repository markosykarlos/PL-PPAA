package monitores;

import java.util.ArrayList;

public class Sotano {
    private ArrayList<String> ninos = new ArrayList<>();

    public synchronized void acceder(String idNino) { ninos.add(idNino); }
    public synchronized void salir(String idNino) { ninos.remove(idNino); }
    
    public synchronized String getIDs() {
        if (ninos.isEmpty()) return "";
        return String.join(", ", ninos);
    }

    public int getNumeroNinos() {
        return ninos.size();
    }
}