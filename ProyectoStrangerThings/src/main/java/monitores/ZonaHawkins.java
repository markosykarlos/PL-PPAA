package monitores;

import java.util.ArrayList;

public class ZonaHawkins {
    private ArrayList<String> ninos = new ArrayList<>();

    public synchronized void entrar(String idNino) { ninos.add(idNino); }
    public synchronized void salir(String idNino) { ninos.remove(idNino); }
    
    public synchronized String getIDs() {
        if (ninos.isEmpty()) return "";
        return String.join(", ", ninos);
    }
}
