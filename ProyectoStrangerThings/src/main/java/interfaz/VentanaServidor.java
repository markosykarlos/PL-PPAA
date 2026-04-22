package interfaz; // Tu paquete

import javax.swing.*;
import java.awt.*;

public class VentanaServidor extends JFrame {

    // Paneles para Hawkins
    private JTextArea txtCallePrincipal;
    private JTextArea txtSotanoByers;
    private JTextArea txtRadioWSQK;
    private JLabel lblSangre;

    // Paneles para Portales
    private JTextArea txtPortalBosque;
    private JTextArea txtPortalLab;
    private JTextArea txtPortalCentro;
    private JTextArea txtPortalAlcantarilla;

    // Paneles para Upside Down
    private JTextArea txtBosque;
    private JTextArea txtLaboratorio;
    private JTextArea txtCentroComercial;
    private JTextArea txtAlcantarillado;
    private JLabel lblColmena;

    public VentanaServidor() {
        setTitle("STRANGER THINGS - Servidor Central");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new GridLayout(1, 3, 10, 10)); 

        add(crearPanelHawkins());
        add(crearPanelPortales());
        add(crearPanelUpsideDown());
    }

    private JPanel crearPanelHawkins() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("HAWKINS (Zona Segura)"));

        txtCallePrincipal = crearAreaTexto("CALLE PRINCIPAL");
        txtSotanoByers = crearAreaTexto("SÓTANO BYERS");
        txtRadioWSQK = crearAreaTexto("RADIO WSQK");
        
        JPanel panelSangre = new JPanel();
        panelSangre.setBorder(BorderFactory.createTitledBorder("SANGRE RECOLECTADA"));
        lblSangre = new JLabel("0 unidades");
        lblSangre.setFont(new Font("Arial", Font.BOLD, 18));
        panelSangre.add(lblSangre);

        panel.add(new JScrollPane(txtCallePrincipal));
        panel.add(new JScrollPane(txtSotanoByers));
        panel.add(new JScrollPane(txtRadioWSQK));
        panel.add(panelSangre);

        return panel;
    }

    private JPanel crearPanelPortales() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("PORTALES (Tránsito)"));

        txtPortalBosque = crearAreaTexto("Portal 1 -> Bosque (Piden 2)");
        txtPortalLab = crearAreaTexto("Portal 2 -> Laboratorio (Piden 3)");
        txtPortalCentro = crearAreaTexto("Portal 3 -> Centro Com. (Piden 4)");
        txtPortalAlcantarilla = crearAreaTexto("Portal 4 -> Alcantarillado (Piden 2)");

        panel.add(new JScrollPane(txtPortalBosque));
        panel.add(new JScrollPane(txtPortalLab));
        panel.add(new JScrollPane(txtPortalCentro));
        panel.add(new JScrollPane(txtPortalAlcantarilla));

        return panel;
    }

    private JPanel crearPanelUpsideDown() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("UPSIDE DOWN (Peligro)"));

        txtBosque = crearAreaTexto("BOSQUE");
        txtLaboratorio = crearAreaTexto("LABORATORIO");
        txtCentroComercial = crearAreaTexto("CENTRO COMERCIAL");
        txtAlcantarillado = crearAreaTexto("ALCANTARILLADO");

        JPanel panelColmena = new JPanel();
        panelColmena.setBorder(BorderFactory.createTitledBorder("COLMENA (Niños Capturados)"));
        lblColmena = new JLabel("0 niños");
        lblColmena.setFont(new Font("Arial", Font.BOLD, 18));
        panelColmena.add(lblColmena);

        panel.add(new JScrollPane(txtBosque));
        panel.add(new JScrollPane(txtLaboratorio));
        panel.add(new JScrollPane(txtCentroComercial));
        panel.add(new JScrollPane(txtAlcantarillado));
        panel.add(panelColmena);

        return panel;
    }

    private JTextArea crearAreaTexto(String titulo) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createTitledBorder(titulo));
        return area;
    }
}