package repository;

import config.Config;
import entities.DistritoElectoral;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DistritoRepository {

    public DistritoElectoral buscarPorCodigo(String codigoElectoral)
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