/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import config.Config;
import entidades.DistritoElectoral;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author jasga
 */


public class DistritoRepository implements RepositorioDistritos{
    
    @Override
    public DistritoElectoral buscarPorCodigo (String codigoElectoral)
        throws IOException {

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(Config.DISTRITOS_PATH))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(",");

                if (datos[0].trim().equals(codigoElectoral)) {

                    return new DistritoElectoral(
                            datos[0].trim(),
                            datos[1].trim(),
                            datos[2].trim(),
                            datos[3].trim()
                    );
                }
            }
        }
        return null;
    }
}
