/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;
import presentacion.http.HttpServerPadron;
import presentacion.tcp.TCPServer;
/**
 *
 * @author jasga
 */
public class Main {
        public static void main(String[] args) {

        Thread hiloTcp = new Thread(() -> {
            TCPServer servidorTcp = new TCPServer();
            servidorTcp.iniciar();
        });

        hiloTcp.setName("servidor tcp");
        hiloTcp.start();

        HttpServerPadron servidorHttp = new HttpServerPadron();
        servidorHttp.iniciar();

        System.out.println(
                "Servidor del Padron Electoral listo "
                        + "(TCP y HTTP corriendo).");
    }
}
