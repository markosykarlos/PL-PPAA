package cliente;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


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

    // Paneles y componentes
    private JTextArea txtResumenHawkins, txtPortales, txtUpsideDown, txtRanking;
    private JLabel lblEventoGlobal, lblEstadoSistema;
    private JButton btnPausarReanudar;

    
    private boolean estaPausado = false;

    public VentanaClienteRMI() {
        setTitle("STRANGER THINGS - Módulo Remoto (Cliente RMI)");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        conectarConServidor();
        iniciarActualizacionAutomatica();

        setVisible(true);
    }

    private void inicializarComponentes() {
        // Panel superior - Estado general
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

        // Paneles principales
        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 10, 10));

        txtResumenHawkins = crearTextArea("RESUMEN HAWKINS");
        txtPortales = crearTextArea("ESTADO DE PORTALES");
        txtUpsideDown = crearTextArea("UPSIDE DOWN");
        txtRanking = crearTextArea("RANKING DEMOGORGONS");

        panelCentral.add(new JScrollPane(txtResumenHawkins));
        panelCentral.add(new JScrollPane(txtPortales));
        panelCentral.add(new JScrollPane(txtUpsideDown));

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(new JScrollPane(txtRanking), BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
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
        // Ejecuta la actualización directamente en el hilo de la interfaz (EDT)
        timerActualizacion = new javax.swing.Timer(800, e -> actualizarDatosRemotos());
        timerActualizacion.start();
    }

    private void actualizarDatosRemotos() {
        if (servidor == null) return;

        try {
            // Resumen Hawkins
            int totalHawkins = servidor.getTotalNinosEnHawkins();
            txtResumenHawkins.setText("Total niños en Hawkins: " + totalHawkins);

            // Portales
            txtPortales.setText(servidor.getEstadoPortales());

            // Upside Down - Niños
            StringBuilder sb = new StringBuilder("=== UPSIDE DOWN ===\n\n");
            sb.append("Bosque: ").append(servidor.getNinosEnBosque()).append(" niños\n");
            sb.append("Laboratorio: ").append(servidor.getNinosEnLaboratorio()).append(" niños\n");
            sb.append("Centro Comercial: ").append(servidor.getNinosEnCentroComercial()).append(" niños\n");
            sb.append("Alcantarillado: ").append(servidor.getNinosEnAlcantarillado()).append(" niños\n");
            sb.append("\nColmena (Capturados): ").append(servidor.getNinosEnColmena()).append(" niños");
            txtUpsideDown.setText(sb.toString());

            // Demogorgons
            // (puedes expandirlo más si quieres)

            // Ranking
            List<String> ranking = servidor.getRankingDemogorgons();
            StringBuilder rank = new StringBuilder("TOP 3 DEMOGORGONS\n\n");
            for (int i = 0; i < ranking.size() && i < 3; i++) {
                rank.append(ranking.get(i)).append("\n");
            }
            txtRanking.setText(rank.toString());

            // Evento Global
            lblEventoGlobal.setText("Evento: " + servidor.getEstadoEventoGlobal());

            // Estado del sistema
            lblEstadoSistema.setText(servidor.isEjecutando() ? 
                "Sistema EJECUTANDO" : "Sistema PAUSADO");
            lblEstadoSistema.setForeground(servidor.isEjecutando() ? Color.GREEN : Color.RED);

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
//        SwingUtilities.invokeLater(() -> new VentanaClienteRMI());
//    }
}
