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
        private final String servidorHost;
    private final int puertoTcp;
    private final int puertoHttp;
    private final int timeoutMs;

    private Configuracion(String servidorHost, int puertoTcp, int puertoHttp, int timeoutMs) {
        this.servidorHost = servidorHost;
        this.puertoTcp = puertoTcp;
        this.puertoHttp = puertoHttp;
        this.timeoutMs = timeoutMs;
    }

    public static Configuracion desde(String rutaArchivoConfig) throws ConfiguracionException {

        Properties propiedades = new Properties();

        try (FileInputStream entrada = new FileInputStream(rutaArchivoConfig)) {
            propiedades.load(entrada);
        } catch (IOException e) {
            throw new ConfiguracionException(
                "No se pudo leer el archivo de configuracion: " + rutaArchivoConfig
                + ". Copie config.properties.example y renombrelo a config.properties.", e);
        }

        String host = obtenerClaveRequerida(propiedades, "servidor.host");
        String puertoTcpTexto = obtenerClaveRequerida(propiedades, "servidor.puerto.tcp");
        String puertoHttpTexto = obtenerClaveRequerida(propiedades, "servidor.puerto.http");
        String timeoutTexto = obtenerClaveRequerida(propiedades, "timeout.ms");

        int puertoTcp = parsearEntero(puertoTcpTexto, "servidor.puerto.tcp");
        int puertoHttp = parsearEntero(puertoHttpTexto, "servidor.puerto.http");
        int timeoutMs = parsearEntero(timeoutTexto, "timeout.ms");

        return new Configuracion(host, puertoTcp, puertoHttp, timeoutMs);
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

    private static int parsearEntero(String texto, String nombreClave)
            throws ConfiguracionException {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new ConfiguracionException(
                "El valor de '" + nombreClave + "' ('" + texto + "') no es un numero valido.", e);
        }
    }

    public String getServidorHost() { return servidorHost; }
    public int getPuertoTcp() { return puertoTcp; }
    public int getPuertoHttp() { return puertoHttp; }
    public int getTimeoutMs() { return timeoutMs; }
}
