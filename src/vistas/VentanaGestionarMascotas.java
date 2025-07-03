package vistas;

import controlador.MascotaController;
import modelo.vo.MascotaVo;

import javax.swing.*;
import java.awt.*;

public class VentanaGestionarMascotas extends JFrame {
    private JTextField txtIdMascota;
    private JTextField txtNombre;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextField txtDocumentoDueno;
    private JTextArea txtComentarios;
    private JTextArea txtAreaInfo;
    private JButton btnRegistrar;
    private JButton btnConsultar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnConsultarLista;

    private MascotaController mascotaController;

    public VentanaGestionarMascotas() {
        this.mascotaController = new MascotaController();
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        setTitle("GESTIONAR MASCOTAS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Campos de texto
        txtIdMascota = new JTextField(15);
        txtNombre = new JTextField(15);
        txtRaza = new JTextField(15);
        txtDocumentoDueno = new JTextField(15);

        // ComboBox para sexo
        cmbSexo = new JComboBox<>(new String[]{"Macho", "Hembra"});

        // Área de comentarios
        txtComentarios = new JTextArea(3, 15);
        txtComentarios.setLineWrap(true);
        txtComentarios.setWrapStyleWord(true);

        // Área de información
        txtAreaInfo = new JTextArea(15, 30);
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Botones
        btnRegistrar = new JButton("Registrar");
        btnConsultar = new JButton("Consultar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnConsultarLista = new JButton("Consultar Lista");

        // Eventos
        btnRegistrar.addActionListener(e -> registrarMascota());
        btnConsultar.addActionListener(e -> consultarMascota());
        btnActualizar.addActionListener(e -> actualizarMascota());
        btnEliminar.addActionListener(e -> eliminarMascota());
        btnConsultarLista.addActionListener(e -> consultarLista());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel izquierdo con formulario
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Datos de Mascota"));
        panelIzquierdo.setPreferredSize(new Dimension(280, 500));

        // Agregar campos al panel izquierdo
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("ID Mascota:", txtIdMascota));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Nombre:", txtNombre));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Raza:", txtRaza));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createComboPanel("Sexo:", cmbSexo));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Doc. Dueño:", txtDocumentoDueno));
        panelIzquierdo.add(Box.createVerticalStrut(10));

        // Panel para comentarios
        JPanel panelComentarios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblComentarios = new JLabel("Comentarios:");
        lblComentarios.setPreferredSize(new Dimension(80, 25));
        panelComentarios.add(lblComentarios);
        panelComentarios.add(new JScrollPane(txtComentarios));
        panelIzquierdo.add(panelComentarios);

        panelIzquierdo.add(Box.createVerticalStrut(20));

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 5, 5));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultarLista);

        panelIzquierdo.add(panelBotones);
        panelIzquierdo.add(Box.createVerticalGlue());

        // Panel derecho con área de texto
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Información"));
        JScrollPane scrollPane = new JScrollPane(txtAreaInfo);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }

    private JPanel createFieldPanel(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(80, 25));
        panel.add(lbl);
        panel.add(field);
        return panel;
    }

    private JPanel createComboPanel(String label, JComboBox<String> combo) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(80, 25));
        panel.add(lbl);
        panel.add(combo);
        return panel;
    }

    private void registrarMascota() {
        String resultado = mascotaController.registrarMascota(
                txtNombre.getText(),
                txtRaza.getText(),
                (String) cmbSexo.getSelectedItem(),
                txtComentarios.getText(),
                txtDocumentoDueno.getText()
        );
        txtAreaInfo.setText(resultado);
        if (resultado.contains("exitosamente")) {
            limpiarCampos();
        }
    }

    private void consultarMascota() {
        String resultado = mascotaController.consultarMascota(txtIdMascota.getText());
        txtAreaInfo.setText(resultado);

        if (!resultado.contains("Error") && !resultado.contains("No se encontró")) {
            MascotaVo mascota = mascotaController.obtenerMascota(txtIdMascota.getText());
            if (mascota != null) {
                cargarDatosEnFormulario(mascota);
            }
        }
    }

    private void actualizarMascota() {
        String resultado = mascotaController.actualizarMascota(
                txtIdMascota.getText(),
                txtNombre.getText(),
                txtRaza.getText(),
                (String) cmbSexo.getSelectedItem(),
                txtComentarios.getText(),
                txtDocumentoDueno.getText()
        );
        txtAreaInfo.setText(resultado);
    }

    private void eliminarMascota() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta mascota?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = mascotaController.eliminarMascota(txtIdMascota.getText());
            txtAreaInfo.setText(resultado);
            if (resultado.contains("exitosamente")) {
                limpiarCampos();
            }
        }
    }

    private void consultarLista() {
        String resultado = mascotaController.listarMascotas();
        txtAreaInfo.setText(resultado);
    }

    private void cargarDatosEnFormulario(MascotaVo mascota) {
        txtIdMascota.setText(String.valueOf(mascota.getIdMascota()));
        txtNombre.setText(mascota.getNombre());
        txtRaza.setText(mascota.getRaza());
        cmbSexo.setSelectedItem(mascota.getSexo());
        txtComentarios.setText(mascota.getComentarios());
        txtDocumentoDueno.setText(mascota.getDocumentoDueno());
    }

    private void limpiarCampos() {
        txtIdMascota.setText("");
        txtNombre.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtComentarios.setText("");
        txtDocumentoDueno.setText("");
    }
}
