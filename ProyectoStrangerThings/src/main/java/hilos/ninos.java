package hilos;

public class ninos extends Thread {
    private String idNino;

    public ninos(int idNumerico) {
        // Esto es para que aparezcan asi N0001, N0023
        this.idNino = String.format("N%04d", idNumerico);
    }

    @Override
    public void run() {
        // Por poner algo
        System.out.println("El niño " + idNino + " ha aparecido en la CALLE PRINCIPAL.");
        
        // Esto es para que nos diga cada seg si sigue vivo
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getIdNino() {
        return idNino;
    }
}