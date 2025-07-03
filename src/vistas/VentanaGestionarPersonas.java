package vistas;

import controlador.PersonaController;
import modelo.vo.PersonaVo;

import javax.swing.*;
import java.awt.*;

public class VentanaGestionarPersonas extends JFrame {
    private JTextField txtDocumento;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextArea txtAreaInfo;
    private JButton btnRegistrar;
    private JButton btnConsultar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnConsultarLista;

    private PersonaController personaController;

    public VentanaGestionarPersonas() {
        this.personaController = new PersonaController();
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        setTitle("GESTIONAR PERSONAS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Campos de texto
        txtDocumento = new JTextField(15);
        txtNombre = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtEmail = new JTextField(15);

        // Área de texto
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
        btnRegistrar.addActionListener(e -> registrarPersona());
        btnConsultar.addActionListener(e -> consultarPersona());
        btnActualizar.addActionListener(e -> actualizarPersona());
        btnEliminar.addActionListener(e -> eliminarPersona());
        btnConsultarLista.addActionListener(e -> consultarLista());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel izquierdo con formulario
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Datos de Persona"));
        panelIzquierdo.setPreferredSize(new Dimension(250, 400));

        // Agregar campos al panel izquierdo
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Documento:", txtDocumento));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Nombre:", txtNombre));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Teléfono:", txtTelefono));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(createFieldPanel("Email:", txtEmail));
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

    private void registrarPersona() {
        String resultado = personaController.registrarPersona(
                txtDocumento.getText(),
                txtNombre.getText(),
                txtTelefono.getText(),
                txtEmail.getText()
        );
        txtAreaInfo.setText(resultado);
        if (resultado.contains("exitosamente")) {
            limpiarCampos();
        }
    }

    private void consultarPersona() {
        String resultado = personaController.consultarPersona(txtDocumento.getText());
        txtAreaInfo.setText(resultado);

        if (!resultado.contains("Error") && !resultado.contains("No se encontró")) {
            PersonaVo persona = personaController.obtenerPersona(txtDocumento.getText());
            if (persona != null) {
                cargarDatosEnFormulario(persona);
            }
        }
    }

    private void actualizarPersona() {
        String resultado = personaController.actualizarPersona(
                txtDocumento.getText(),
                txtNombre.getText(),
                txtTelefono.getText(),
                txtEmail.getText()
        );
        txtAreaInfo.setText(resultado);
    }

    private void eliminarPersona() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta persona?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = personaController.eliminarPersona(txtDocumento.getText());
            txtAreaInfo.setText(resultado);
            if (resultado.contains("exitosamente")) {
                limpiarCampos();
            }
        }
    }

    private void consultarLista() {
        String resultado = personaController.listarPersonas();
        txtAreaInfo.setText(resultado);
    }

    private void cargarDatosEnFormulario(PersonaVo persona) {
        txtDocumento.setText(persona.getDocumento());
        txtNombre.setText(persona.getNombre());
        txtTelefono.setText(persona.getTelefono());
        txtEmail.setText(persona.getEmail());
    }

    private void limpiarCampos() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }
}
