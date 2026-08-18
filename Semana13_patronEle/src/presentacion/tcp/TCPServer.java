/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.tcp;
import config.Configuracion;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author jasga
 */
public class TCPServer {
    
    private final Configuracion configuracion;

    public TCPServer(Configuracion configuracion) {
        this.configuracion = configuracion;
    }
    
    
    public void iniciar(){
            ExecutorService pool =
                Executors.newCachedThreadPool();

        try (ServerSocket servidor = new ServerSocket(configuracion.getPuertoTcp())) {

            System.out.println(
                    "Servidor TCP iniciado en puerto "
                    + configuracion.getPuertoTcp());

            while (true) {

                Socket cliente =
                        servidor.accept();

                pool.execute(new TCPClienteHandler(cliente, configuracion));
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}