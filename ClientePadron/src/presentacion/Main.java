/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;
import com.formdev.flatlaf.FlatLightLaf;
import config.Configuracion;
import excepciones.ConfiguracionException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author jasga
 */
public class Main {
    
       public static void main(String[] args) {

        FlatLightLaf.setup();

        Configuracion configuracion;

        try {

            configuracion = Configuracion.desde("config.properties");

        } catch (ConfiguracionException ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo iniciar la aplicación:\n" + ex.getMessage(),
                    "Error de configuración",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        Configuracion configFinal = configuracion;

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(configFinal);
            ventana.setVisible(true);
        });
    } 
    
}
