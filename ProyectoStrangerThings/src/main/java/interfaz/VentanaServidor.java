package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import monitores.*;

public class VentanaServidor extends JFrame {
    // Componentes de la Interfaz
    private JTextArea txtCallePrincipal, txtSotanoByers, txtRadioWSQK;
    private JTextArea txtPortalBosque, txtPortalLab, txtPortalCentro, txtPortalAlcantarilla;
    private JTextArea txtBosque, txtLaboratorio, txtCentroComercial, txtAlcantarillado;
    private JLabel lblSangre, lblColmena, lblEventoGlobal;

    // Referencias a los 14 Monitores y Portales
    private Sangre sangre;
    private Colmena colmena;
    private Bosque bosque;
    private Laboratorio laboratorio;
    private CentroComercial centroComercial;
    private Alcantarillado alcantarillado;
    private EstadoGlobal estadoGlobal;
    private Sotano sotano;
    private ZonaHawkins callePrincipal;
    private ZonaHawkins radioWSQK;
    private Portal portalBosque, portalLab, portalCC, portalAlc;

    // CONSTRUCTOR ACTUALIZADO: Ahora acepta los 14 parámetros
    public VentanaServidor(Sangre sangre, Colmena colmena, Bosque bosque, Laboratorio lab, CentroComercial cc, 
                           Alcantarillado alc, EstadoGlobal estado, Sotano sotano, ZonaHawkins calle, 
                           ZonaHawkins radio, Portal p1, Portal p2, Portal p3, Portal p4) {
        this.sangre = sangre;
        this.colmena = colmena;
        this.bosque = bosque;
        this.laboratorio = lab;
        this.centroComercial = cc;
        this.alcantarillado = alc;
        this.estadoGlobal = estado;
        this.sotano = sotano;
        this.callePrincipal = calle;
        this.radioWSQK = radio;
        this.portalBosque = p1;
        this.portalLab = p2;
        this.portalCC = p3;
        this.portalAlc = p4;

        setTitle("STRANGER THINGS - Servidor Central");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 3, 10, 10));

        add(crearPanelHawkins());
        add(crearPanelPortales());
        add(crearPanelUpsideDown());

        iniciarActualizador();
    }

    private JPanel crearPanelHawkins() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("HAWKINS (Zona Segura)"));
        txtCallePrincipal = crearAreaTexto("CALLE PRINCIPAL");
        txtSotanoByers = crearAreaTexto("SÓTANO BYERS");
        txtRadioWSQK = crearAreaTexto("RADIO WSQK");
        
        lblSangre = new JLabel("0 unidades", SwingConstants.CENTER);
        lblSangre.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel pSangre = new JPanel(new BorderLayout());
        pSangre.setBorder(BorderFactory.createTitledBorder("SANGRE RECOLECTADA"));
        pSangre.add(lblSangre);

        lblEventoGlobal = new JLabel("NINGUNO", SwingConstants.CENTER);
        lblEventoGlobal.setFont(new Font("Arial", Font.BOLD, 14));
        lblEventoGlobal.setForeground(Color.RED);
        JPanel pEvento = new JPanel(new BorderLayout());
        pEvento.setBorder(BorderFactory.createTitledBorder("EVENTO GLOBAL ACTIVO"));
        pEvento.add(lblEventoGlobal);

        panel.add(new JScrollPane(txtCallePrincipal));
        panel.add(new JScrollPane(txtSotanoByers));
        panel.add(new JScrollPane(txtRadioWSQK));
        panel.add(pSangre);
        panel.add(pEvento);
        return panel;
    }

    private JPanel crearPanelPortales() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("PORTALES (Tránsito)"));
        txtPortalBosque = crearAreaTexto("Portal 1 -> Bosque (Piden 2)");
        txtPortalLab = crearAreaTexto("Portal 2 -> Lab (Piden 3)");
        txtPortalCentro = crearAreaTexto("Portal 3 -> CC (Piden 4)");
        txtPortalAlcantarilla = crearAreaTexto("Portal 4 -> Alcantarilla (Piden 2)");
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
        
        lblColmena = new JLabel("0 niños", SwingConstants.CENTER);
        lblColmena.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel pCol = new JPanel(new BorderLayout());
        pCol.setBorder(BorderFactory.createTitledBorder("COLMENA (Niños Capturados)"));
        pCol.add(lblColmena);

        panel.add(new JScrollPane(txtBosque));
        panel.add(new JScrollPane(txtLaboratorio));
        panel.add(new JScrollPane(txtCentroComercial));
        panel.add(new JScrollPane(txtAlcantarillado));
        panel.add(pCol);
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

    private void iniciarActualizador() {
        new Timer(500, e -> actualizarInterfaz()).start();
    }

    private void actualizarInterfaz() {
        // 1. Hawkins
        txtCallePrincipal.setText(callePrincipal.getIDs());
        txtSotanoByers.setText(sotano.getIDs());
        txtRadioWSQK.setText(radioWSQK.getIDs());
        lblSangre.setText(sangre.getCantidad() + " unidades");

        // 2. Portales
        txtPortalBosque.setText(portalBosque.getIDs());
        txtPortalLab.setText(portalLab.getIDs());
        txtPortalCentro.setText(portalCC.getIDs());
        txtPortalAlcantarilla.setText(portalAlc.getIDs());

        // 3. Upside Down (Niños y Demogorgons)
        txtBosque.setText("N: " + bosque.getIDsNinos() + "\nD: " + bosque.getIDsDemogorgons());
        txtLaboratorio.setText("N: " + laboratorio.getIDsNinos() + "\nD: " + laboratorio.getIDsDemogorgons());
        txtCentroComercial.setText("N: " + centroComercial.getIDsNinos() + "\nD: " + centroComercial.getIDsDemogorgons());
        txtAlcantarillado.setText("N: " + alcantarillado.getIDsNinos() + "\nD: " + alcantarillado.getIDsDemogorgons());
        lblColmena.setText(colmena.getTotalNinos() + " niños: " + colmena.getIDs());

        // 4. Evento
        actualizarEvento();
    }

    private void actualizarEvento() {
        switch (estadoGlobal.getEventoActivo()) {
            case EstadoGlobal.NINGUNO: lblEventoGlobal.setText("NINGUNO"); break;
            case EstadoGlobal.APAGON_LABORATORIO: lblEventoGlobal.setText("APAGÓN DEL LABORATORIO"); break;
            case EstadoGlobal.TORMENTA_UPSIDEDOWN: lblEventoGlobal.setText("TORMENTA DEL UPSIDE DOWN"); break;
            case EstadoGlobal.INTERVENCION_ELEVEN: lblEventoGlobal.setText("INTERVENCIÓN DE ELEVEN"); break;
            case EstadoGlobal.RED_MENTAL: lblEventoGlobal.setText("LA RED MENTAL"); break;
        }
    }
}