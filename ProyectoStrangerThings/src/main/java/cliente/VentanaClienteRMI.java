package cliente;
import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import monitores.*;
import main.*;
import rmi.*;

public class VentanaClienteRMI extends JFrame {

    private HawkinsRemote servidor;

    private javax.swing.Timer timerActualizacion;

    private JTextArea txtResumenHawkins, txtPortales, txtUpsideDown, txtRanking;
    private JLabel lblEventoGlobal, lblEstadoSistema;
    private JButton btnPausarReanudar;
   
    private boolean estaPausado = false;

    public VentanaClienteRMI() {
        setTitle("STRANGER THINGS - Módulo Remoto (Cliente RMI)");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        conectarConServidor();
        iniciarActualizacionAutomatica();

        setVisible(true);
    }

    private void inicializarComponentes() {
        // Panel superior
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 10, 10));
       
        lblEstadoSistema = new JLabel("Conectado al servidor", SwingConstants.CENTER);
        lblEstadoSistema.setFont(new Font("Arial", Font.BOLD, 16));
        lblEstadoSistema.setForeground(Color.GREEN);
       
        lblEventoGlobal = new JLabel("Evento: Sin evento activo", SwingConstants.CENTER);
        lblEventoGlobal.setFont(new Font("Arial", Font.BOLD, 14));
       
        btnPausarReanudar = new JButton("PAUSAR SISTEMA");
        btnPausarReanudar.addActionListener(e -> togglePausaSistema());
        
        panelSuperior.add(lblEstadoSistema);
        panelSuperior.add(lblEventoGlobal);
        panelSuperior.add(btnPausarReanudar);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        // Izquierda: Resumen Hawkins
        JPanel leftPanel = new JPanel(new BorderLayout());
        txtResumenHawkins = crearTextArea("RESUMEN HAWKINS");
        leftPanel.add(new JScrollPane(txtResumenHawkins), BorderLayout.CENTER);

        // Centro: Portales arriba + Upside Down abajo
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        txtPortales = crearTextArea("ESTADO DE PORTALES");
        txtUpsideDown = crearTextArea("UPSIDE DOWN");
        centerPanel.add(new JScrollPane(txtPortales));
        centerPanel.add(new JScrollPane(txtUpsideDown));

        // Derecha: Ranking
        JPanel rightPanel = new JPanel(new BorderLayout());
        txtRanking = crearTextArea("RANKING DEMOGORGONS");
        rightPanel.add(new JScrollPane(txtRanking), BorderLayout.CENTER);

        panelCentral.add(leftPanel, BorderLayout.WEST);
        panelCentral.add(centerPanel, BorderLayout.CENTER);
        panelCentral.add(rightPanel, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private JTextArea crearTextArea(String titulo) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createTitledBorder(titulo));
        return area;
    }

    private void conectarConServidor() {
        try {
            servidor = (HawkinsRemote) Naming.lookup("rmi://localhost:1099/HawkinsServer");
            System.out.println("Conectado exitosamente al servidor RMI");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "No se pudo conectar al servidor.\nError: " + e.getMessage(),
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void iniciarActualizacionAutomatica() {
        timerActualizacion = new javax.swing.Timer(800, e -> actualizarDatosRemotos());
        timerActualizacion.start();
    }

    private void actualizarDatosRemotos() {
        if (servidor == null) return;
        try {
            // Resumen Hawkins
            txtResumenHawkins.setText("Total niños en Hawkins: " + servidor.getTotalNinosEnHawkins());

            // Portales
            txtPortales.setText(servidor.getEstadoPortales());

            // Upside Down
            StringBuilder sb = new StringBuilder("=== UPSIDE DOWN ===\n\n");
            sb.append("Bosque: ").append(servidor.getNinosEnBosque()).append(" niños\n");
            sb.append("Laboratorio: ").append(servidor.getNinosEnLaboratorio()).append(" niños\n");
            sb.append("Centro Comercial: ").append(servidor.getNinosEnCentroComercial()).append(" niños\n");
            sb.append("Alcantarillado: ").append(servidor.getNinosEnAlcantarillado()).append(" niños\n\n");
            sb.append("Colmena (Capturados): ").append(servidor.getNinosEnColmena()).append(" niños");
            txtUpsideDown.setText(sb.toString());

            // Ranking
            List<String> ranking = servidor.getRankingDemogorgons();
            StringBuilder rank = new StringBuilder("TOP 3 DEMOGORGONS\n\n");
            for (String r : ranking) {
                rank.append(r).append("\n");
            }
            txtRanking.setText(rank.toString());

            // Evento Global
            lblEventoGlobal.setText("Evento: " + servidor.getEstadoEventoGlobal());

            // Estado del sistema
            boolean ejecutando = servidor.isEjecutando();
            lblEstadoSistema.setText(ejecutando ? "Sistema EJECUTANDO" : "Sistema PAUSADO");
            lblEstadoSistema.setForeground(ejecutando ? Color.GREEN : Color.RED);

        } catch (RemoteException e) {
            System.err.println("Error al actualizar datos remotos: " + e.getMessage());
        }
    }

    private void togglePausaSistema() {
        if (servidor == null) return;
        try {
            if (estaPausado) {
                servidor.reanudarEjecucion();
                btnPausarReanudar.setText("PAUSAR SISTEMA");
            } else {
                servidor.pausarEjecucion();
                btnPausarReanudar.setText("REANUDAR SISTEMA");
            }
            estaPausado = !estaPausado;
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(VentanaClienteRMI::new);
//    }
}