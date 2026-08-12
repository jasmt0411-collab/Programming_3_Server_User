package tcp;

import config.Config;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    public void iniciar() {

        ExecutorService pool =
                Executors.newFixedThreadPool(10);

        try (ServerSocket servidor =
                     new ServerSocket(Config.TCP_PORT)) {

            System.out.println(
                    "Servidor TCP iniciado en puerto "
                    + Config.TCP_PORT);

            while (true) {

                Socket cliente =
                        servidor.accept();

                pool.execute(
                        new TCPClienteHandler(cliente));
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}