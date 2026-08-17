/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author jasga
 */
public class ClienteTCPPrueba {
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

            salida.println("GET|118270375");

            String respuesta =
                    entrada.readLine();

            System.out.println(respuesta);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    } 
}
