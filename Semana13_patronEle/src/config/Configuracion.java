/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import excepciones.ConfiguracionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 *
 * @author jasga
 */
public class Configuracion {
     private final int puertoTcp;
    private final int puertoHttp;
    private final String rutaPadron;
    private final String rutaDistelec;

    private Configuracion(int puertoTcp, int puertoHttp, String rutaPadron, String rutaDistelec) {
        this.puertoTcp = puertoTcp;
        this.puertoHttp = puertoHttp;
        this.rutaPadron = rutaPadron;
        this.rutaDistelec = rutaDistelec;
    }

    public static Configuracion desde(String rutaArchivoConfig) throws ConfiguracionException {
        Properties propiedades = new Properties();

        try (FileInputStream entrada = new FileInputStream(rutaArchivoConfig)) {
            propiedades.load(entrada);
        } catch (IOException e) {
            throw new ConfiguracionException(
                "No se pudo leer el archivo de configuracion: " + rutaArchivoConfig
                + ". Verifique que el archivo exista (copie config.properties.example "
                + "y renombrelo a config.properties).", e);
        }

        String puertoTcpTexto = obtenerClaveRequerida(propiedades, "puerto.tcp");
        String puertoHttpTexto = obtenerClaveRequerida(propiedades, "puerto.http");
        String rutaPadron = obtenerClaveRequerida(propiedades, "ruta.padron");
        String rutaDistelec = obtenerClaveRequerida(propiedades, "ruta.distelec");

        int puertoTcp = parsearPuerto(puertoTcpTexto, "puerto.tcp");
        int puertoHttp = parsearPuerto(puertoHttpTexto, "puerto.http");

        return new Configuracion(puertoTcp, puertoHttp, rutaPadron, rutaDistelec);
    }

    private static String obtenerClaveRequerida(Properties propiedades, String clave)
            throws ConfiguracionException {
        String valor = propiedades.getProperty(clave);
        if (valor == null || valor.trim().isEmpty()) {
            throw new ConfiguracionException(
                "Falta la clave requerida '" + clave + "' en config.properties.");
        }
        return valor.trim();
    }

    private static int parsearPuerto(String texto, String nombreClave) throws ConfiguracionException {
        try {
            int puerto = Integer.parseInt(texto);
            if (puerto < 1 || puerto > 65535) {
                throw new ConfiguracionException(
                    "El valor de '" + nombreClave + "' (" + puerto + ") no es un puerto valido "
                    + "(debe estar entre 1 y 65535).");
            }
            return puerto;
        } catch (NumberFormatException e) {
            throw new ConfiguracionException(
                "El valor de '" + nombreClave + "' ('" + texto + "') no es un numero valido.", e);
        }
    }

    public int getPuertoTcp() {
        return puertoTcp;
    }

    public int getPuertoHttp() {
        return puertoHttp;
    }

    public String getRutaPadron() {
        return rutaPadron;
    }

    public String getRutaDistelec() {
        return rutaDistelec;
    }
}
