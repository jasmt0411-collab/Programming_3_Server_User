package repository;

import config.Config;
import entities.Persona;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PadronRepository {

    public Persona buscarPorCedula(String cedula) throws IOException {

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(Config.PADRON_PATH))) {

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