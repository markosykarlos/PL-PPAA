package cliente;
import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.border.TitledBorder;
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
        
       try {
            Color textoFosforo = new Color(50, 255, 50); // Verde brillante retro
            Font fuenteRetro = new Font("Monospaced", Font.BOLD, 13); // Fuente retro

            UIManager.put("TitledBorder.titleColor", textoFosforo);
            UIManager.put("TitledBorder.font", fuenteRetro);

            UIManager.put("Button.background", new Color(30, 40, 30));
            UIManager.put("Button.foreground", textoFosforo);
            UIManager.put("Button.font", new Font("Monospaced", Font.BOLD, 14));
        } catch (Exception e) {
            System.err.println("Error aplicando el tema retro: " + e.getMessage());
        }        
        
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
        panelSuperior.setOpaque(false); // Hacemos transparente también el panel de arriba
        
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

        // Panel central con nuestra imagen
        PanelConFondo panelCentral = new PanelConFondo(); 
        panelCentral.setLayout(new BorderLayout(10, 10));
        panelCentral.setOpaque(true);

        // Izquierda: Resumen Hawkins
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false); // Transparente!
        txtResumenHawkins = crearTextArea("RESUMEN HAWKINS");
        JScrollPane scrollResumen = new JScrollPane(txtResumenHawkins);
        scrollResumen.setOpaque(false);
        scrollResumen.getViewport().setOpaque(false);
        leftPanel.add(scrollResumen, BorderLayout.CENTER);

        // Centro: Portales arriba + Upside Down abajo
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        centerPanel.setOpaque(false); // Transparente
        txtPortales = crearTextArea("ESTADO DE PORTALES");
        txtUpsideDown = crearTextArea("UPSIDE DOWN");
        
        JScrollPane scrollPortales = new JScrollPane(txtPortales);
        scrollPortales.setOpaque(false);
        scrollPortales.getViewport().setOpaque(false);
        centerPanel.add(scrollPortales);
        
        JScrollPane scrollUD = new JScrollPane(txtUpsideDown);
        scrollUD.setOpaque(false);
        scrollUD.getViewport().setOpaque(false);
        centerPanel.add(scrollUD);

        // Derecha: Ranking
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false); // Transparente!
        txtRanking = crearTextArea("RANKING DEMOGORGONS");
        JScrollPane scrollRanking = new JScrollPane(txtRanking);
        scrollRanking.setOpaque(false);
        scrollRanking.getViewport().setOpaque(false);
        rightPanel.add(scrollRanking, BorderLayout.CENTER);

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
        area.setForeground(new Color(50, 255, 50)); // Verde fosforito manual

        area.setOpaque(false); // Esto hace que el fondo de la JTextArea desaparezca
       
        TitledBorder borde = BorderFactory.createTitledBorder(titulo);
        borde.setTitleColor(new Color(50, 255, 50));
        area.setBorder(borde);
        return area;
    }

    private void conectarConServidor() {
        try {
            servidor = (HawkinsRemote) Naming.lookup("//localhost/HawkinsServer");
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
            // 1. Resumen Hawkins
            txtResumenHawkins.setText("Total niños en Hawkins: " + servidor.getTotalNinosEnHawkins()); 
            // 2. Portales
            txtPortales.setText(servidor.getEstadoPortales()); 
            // 3. Upside Down (Niños y Demogorgons)
            StringBuilder sb = new StringBuilder("=== UPSIDE DOWN ===\n\n"); 
            // Sección Niños
            sb.append("--- NIÑOS ---\n");
            sb.append("Bosque: ").append(servidor.getNinosEnBosque()).append(" niños\n"); 
            sb.append("Laboratorio: ").append(servidor.getNinosEnLaboratorio()).append(" niños\n"); 
            sb.append("Centro Comercial: ").append(servidor.getNinosEnCentroComercial()).append(" niños\n"); 
            sb.append("Alcantarillado: ").append(servidor.getNinosEnAlcantarillado()).append(" niños\n\n"); 
            // Sección Demogorgons
            sb.append("--- DEMOGORGONS ---\n");
            sb.append("Bosque: [").append(servidor.getDemogorgonsEnBosque()).append("] IDs: ")
              .append(servidor.getIDsDemogorgonsEnBosque()).append("\n");
            sb.append("Lab: [").append(servidor.getDemogorgonsEnLaboratorio()).append("] IDs: ")
              .append(servidor.getIDsDemogorgonsEnLaboratorio()).append("\n");
            sb.append("CC: [").append(servidor.getDemogorgonsEnCentroComercial()).append("] IDs: ")
              .append(servidor.getIDsDemogorgonsEnCentroComercial()).append("\n");
            sb.append("Alcantarilla: [").append(servidor.getDemogorgonsEnAlcantarillado()).append("] IDs: ")
              .append(servidor.getIDsDemogorgonsEnAlcantarillado()).append("\n\n");

            // Colmena
            sb.append("Colmena (Capturados): ").append(servidor.getNinosEnColmena()).append(" niños"); 
            
            // Actualizamos el JTextArea con todo el contenido acumulado
            txtUpsideDown.setText(sb.toString()); 

            // 4. Ranking
            List<String> ranking = servidor.getRankingDemogorgons(); 
            StringBuilder rank = new StringBuilder("TOP 3 DEMOGORGONS\n\n");
            for (String r : ranking) {
                rank.append(r).append("\n"); 
            }
            txtRanking.setText(rank.toString()); 

            // 5. Evento Global
            lblEventoGlobal.setText("Evento: " + servidor.getEstadoEventoGlobal()); 

            // 6. Estado del sistema (Pausa/Ejecución)
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
  class PanelConFondo extends JPanel {
        private Image imagenFondo;

        public PanelConFondo() {
            try {
                
                java.net.URL url = getClass().getResource("/cliente/fondo_retro.png");
                if (url != null) {
                    
                    imagenFondo = new ImageIcon(url).getImage();
                } else {
                    System.err.println("No se encontro la imagen");
                   
                    setBackground(new Color(15, 20, 15));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}