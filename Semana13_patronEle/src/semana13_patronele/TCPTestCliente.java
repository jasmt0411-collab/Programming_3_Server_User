package semana13_patronele;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPTestCliente {

    public static void main(String[] args) {

        try (
                Socket socket =
                        new Socket("localhost", 5000);

                PrintWriter salida =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                BufferedReader entrada =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));
        ) {

            salida.println("GET|115550555");

            String respuesta =
                    entrada.readLine();

            System.out.println(respuesta);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}