package semana13_patronele;

import http.HttpServerPadron;
import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

public class Prueba {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);

            HttpServerPadron servidor =
                    new HttpServerPadron();

            servidor.iniciar();

            System.out.println();
            System.out.println("=== CONSULTA PADRON ELECTORAL ===");
            System.out.print("Ingrese numero de cedula: ");

            String cedula = sc.nextLine();

            String url =
                    "http://localhost:8080/padron/" + cedula;

            System.out.println();
            System.out.println("Abriendo navegador...");
            System.out.println(url);

            Desktop.getDesktop()
                    .browse(new URI(url));

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}