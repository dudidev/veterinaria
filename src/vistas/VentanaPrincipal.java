package vistas;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JButton btnGestionarPersonas;
    private JButton btnGestionarMascotas;

    public VentanaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("SISTEMA VETERINARIA ABC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fondo
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/bg.jpg"));
        Image imagenEscalada = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        JLabel fondoLabel = new JLabel(iconoEscalado);
        fondoLabel.setBounds(0, 0, getWidth(), getHeight());

        // Crear botones
        btnGestionarPersonas = new JButton("Gestionar Personas");
        btnGestionarMascotas = new JButton("Gestionar Mascotas");

        // Configurar botones
        btnGestionarPersonas.setPreferredSize(new Dimension(200, 50));
        btnGestionarMascotas.setPreferredSize(new Dimension(200, 50));

        // Eventos
        btnGestionarPersonas.addActionListener(e -> abrirGestionPersonas());
        btnGestionarMascotas.addActionListener(e -> abrirGestionMascotas());

        setLayout(null);

        JPanel panelContenido = new JPanel(null);
        panelContenido.setOpaque(false);
        panelContenido.setBounds(0, 0, getWidth(), getHeight());

        JLabel labelTitulo = new JLabel("VETERINARIA ABC", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setForeground(Color.BLACK);
        labelTitulo.setBounds(0, 30, getWidth(), 50);

        btnGestionarPersonas.setBounds(40, 300, 200, 50);
        btnGestionarMascotas.setBounds(260, 300, 200, 50);

        panelContenido.add(labelTitulo);
        panelContenido.add(btnGestionarPersonas);
        panelContenido.add(btnGestionarMascotas);

        setContentPane(new JLayeredPane());
        getContentPane().add(fondoLabel, Integer.valueOf(0)); // fondo abajo
        getContentPane().add(panelContenido, Integer.valueOf(1)); // contenido encima
    }

    private void abrirGestionPersonas() {
        new VentanaGestionarPersonas().setVisible(true);
    }

    private void abrirGestionMascotas() {
        new VentanaGestionarMascotas().setVisible(true);
    }
}
