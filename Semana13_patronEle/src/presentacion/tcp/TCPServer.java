/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.tcp;
import config.Config;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author jasga
 */
public class TCPServer {
    
    public void iniciar(){
            ExecutorService pool =
                Executors.newFixedThreadPool(10);

        try (ServerSocket servidor = new ServerSocket(Config.TCP_PORT)) {

            System.out.println(
                    "Servidor TCP iniciado en puerto "
                    + Config.TCP_PORT);

            while (true) {

                Socket cliente =
                        servidor.accept();

                pool.execute(new TCPClienteHandler(cliente));
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
