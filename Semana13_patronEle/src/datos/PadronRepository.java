/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import config.Configuracion;
import entidades.Persona;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author jasga
 */
public class PadronRepository implements RepositorioPadron {
    
    private final Configuracion configuracion;

    public PadronRepository(Configuracion configuracion) {
        this.configuracion = configuracion;
    }
    
    @Override
    public Persona buscarPorCedula(String cedula) throws IOException {

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(configuracion.getRutaPadron()))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(",");

                if (datos[0].trim().equals(cedula)) {

                    return new Persona(
                            datos[0].trim(),
                            datos[1].trim(),
                            datos[4].trim(),
                            datos[5].trim(),
                            datos[6].trim()
                    );
                }
            }
        }
        return null;
    }
}
