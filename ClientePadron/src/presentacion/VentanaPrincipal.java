/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;
import config.Configuracion;
import logica.ServicioConsulta;
import util.Validador;
import dto.PersonaDTO;
import excepciones.ComunicacionException;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author jasga
 */
public class VentanaPrincipal extends JFrame{
    
    private final ServicioConsulta servicioConsulta;

    private JTextField campoCedula;
    private JRadioButton radioTcp;
    private JRadioButton radioHttp;
    private JButton botonConsultar;
    private JButton botonLimpiar;
    private JButton botonSalir;

    private JTextField campoNombre;
    private JTextField campoPrimerApellido;
    private JTextField campoSegundoApellido;
    private JTextField campoCodigoElectoral;
    private JTextField campoProvincia;
    private JTextField campoCanton;
    private JTextField campoDistrito;

    private JLabel labelEstado;

    public VentanaPrincipal(Configuracion configuracion) {

        this.servicioConsulta = new ServicioConsulta(configuracion);

        configurarVentana();
        construirComponentes();
    }

    private void configurarVentana() {
        setTitle("Consulta del Padrón Electoral");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 480);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirComponentes() {

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        panelPrincipal.add(construirPanelConsulta());
        panelPrincipal.add(Box.createVerticalStrut(12));
        panelPrincipal.add(construirPanelResultado());
        panelPrincipal.add(Box.createVerticalStrut(12));
        panelPrincipal.add(construirPanelEstado());

        setContentPane(panelPrincipal);
    }

    private JPanel construirPanelConsulta() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Consulta"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Cédula:"), c);

        campoCedula = new JTextField(15);
        c.gridx = 1; c.gridy = 0; c.weightx = 1;
        panel.add(campoCedula, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        panel.add(new JLabel("Protocolo:"), c);

        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        radioTcp = new JRadioButton("TCP", true);
        radioHttp = new JRadioButton("HTTP");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioTcp);
        grupo.add(radioHttp);
        panelRadios.add(radioTcp);
        panelRadios.add(radioHttp);

        c.gridx = 1; c.gridy = 1;
        panel.add(panelRadios, c);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botonConsultar = new JButton("Consultar");
        botonLimpiar = new JButton("Limpiar");
        botonSalir = new JButton("Salir");
        panelBotones.add(botonConsultar);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonSalir);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        panel.add(panelBotones, c);

        botonConsultar.addActionListener(e -> consultar());
        botonLimpiar.addActionListener(e -> limpiar());
        botonSalir.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel construirPanelResultado() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la persona"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] etiquetas = {
            "Nombre:", "Primer apellido:", "Segundo apellido:",
            "Código electoral:", "Provincia:", "Cantón:", "Distrito:"
        };

        campoNombre = new JTextField();
        campoPrimerApellido = new JTextField();
        campoSegundoApellido = new JTextField();
        campoCodigoElectoral = new JTextField();
        campoProvincia = new JTextField();
        campoCanton = new JTextField();
        campoDistrito = new JTextField();

        JTextField[] campos = {
            campoNombre, campoPrimerApellido, campoSegundoApellido,
            campoCodigoElectoral, campoProvincia, campoCanton, campoDistrito
        };

        for (JTextField campo : campos) {
            campo.setEditable(false);
        }

        for (int i = 0; i < etiquetas.length; i++) {
            c.gridx = 0; c.gridy = i; c.weightx = 0;
            panel.add(new JLabel(etiquetas[i]), c);

            c.gridx = 1; c.gridy = i; c.weightx = 1;
            panel.add(campos[i], c);
        }

        return panel;
    }

    private JPanel construirPanelEstado() {

        JPanel panel = new JPanel(new BorderLayout());
        labelEstado = new JLabel(" ");
        labelEstado.setForeground(Color.DARK_GRAY);
        panel.add(labelEstado, BorderLayout.CENTER);
        return panel;
    }

    private void consultar() {

    String cedula = campoCedula.getText().trim();

        if (!Validador.esCedulaValida(cedula)) {
            mostrarEstado("La cédula está vacía o no tiene un formato válido.", true);
            return;
        }

        ServicioConsulta.Protocolo protocolo =
                radioTcp.isSelected()
                        ? ServicioConsulta.Protocolo.TCP
                        : ServicioConsulta.Protocolo.HTTP;

        establecerControlesHabilitados(false);
        mostrarEstado("Consultando...", false);
        limpiarResultado();

        SwingWorker<PersonaDTO, Void> tarea = new SwingWorker<>() {

            private ComunicacionException errorOcurrido;

            @Override
            protected PersonaDTO doInBackground() {
                try {
                    return servicioConsulta.consultar(cedula, protocolo);
                } catch (ComunicacionException ex) {
                    errorOcurrido = ex;
                    return null;
                }
            }

            @Override
            protected void done() {

                establecerControlesHabilitados(true);

                if (errorOcurrido != null) {
                    // Regla de robustez: un error de comunicacion nunca
                    // debe cerrar la aplicacion, solo informarse al usuario.
                    mostrarEstado(errorOcurrido.getMessage(), true);
                    return;
                }

                try {
                    PersonaDTO persona = get();
                    mostrarResultado(persona);
                    mostrarEstado("Consulta realizada con éxito (" + protocolo + ").", false);
                } catch (Exception ex) {
                    mostrarEstado("Ocurrió un error inesperado: " + ex.getMessage(), true);
                }
            }
        };

        tarea.execute();
    }
    
    private void mostrarResultado(PersonaDTO persona) {
        campoNombre.setText(persona.getNombre());
        campoPrimerApellido.setText(persona.getPrimerApellido());
        campoSegundoApellido.setText(persona.getSegundoApellido());
        campoCodigoElectoral.setText(persona.getCodigoElectoral());
        campoProvincia.setText(persona.getProvincia());
        campoCanton.setText(persona.getCanton());
        campoDistrito.setText(persona.getDistrito());
    }

    private void establecerControlesHabilitados(boolean habilitados) {
        botonConsultar.setEnabled(habilitados);
        campoCedula.setEnabled(habilitados);
        radioTcp.setEnabled(habilitados);
        radioHttp.setEnabled(habilitados);
    }

    private void limpiar() {
        campoCedula.setText("");
        limpiarResultado();
        mostrarEstado(" ", false);
    }

    private void limpiarResultado() {
        campoNombre.setText("");
        campoPrimerApellido.setText("");
        campoSegundoApellido.setText("");
        campoCodigoElectoral.setText("");
        campoProvincia.setText("");
        campoCanton.setText("");
        campoDistrito.setText("");
    }

    private void mostrarEstado(String mensaje, boolean esError) {
        labelEstado.setText(mensaje);
        labelEstado.setForeground(esError ? new Color(0xB0, 0x00, 0x20) : Color.DARK_GRAY);
    } 
    
}
