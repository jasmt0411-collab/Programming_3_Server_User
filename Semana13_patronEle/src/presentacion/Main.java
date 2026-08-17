/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;
import config.Configuracion;
import excepciones.ConfiguracionException;
import presentacion.http.HttpServerPadron;
import presentacion.tcp.TCPServer;
/**
 *
 * @author jasga
 */
public class Main {
        public static void main(String[] args) {

        Configuracion configuracion;

        try {

            configuracion = Configuracion.desde("config.properties");

        } catch (ConfiguracionException ex) {

            System.err.println(
                    "No se pudo iniciar el servidor: " + ex.getMessage());

            return;
        }

        Thread hiloTcp = new Thread(() -> {
            TCPServer servidorTcp = new TCPServer(configuracion);
            servidorTcp.iniciar();
        });

        hiloTcp.setName("servidor tcp");
        hiloTcp.start();

        HttpServerPadron servidorHttp = new HttpServerPadron(configuracion);
        servidorHttp.iniciar();

        System.out.println(
                "Servidor del Padron Electoral listo "
                        + "(TCP y HTTP corriendo).");
    }
}
