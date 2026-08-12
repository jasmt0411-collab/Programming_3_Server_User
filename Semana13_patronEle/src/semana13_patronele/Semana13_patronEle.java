import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


 
public class Semana13_patronEle {
 
    private static final Scanner sc = new Scanner(System.in);
    private static final String archivoPadron = "C:\\Users\\jcand\\OneDrive - Universidad Latina\\ulatina\\ingenieria en sistemas\\2026\\progra3\\Examen2\\PADRON_COMPLETO.txt";
    private static final String archivoDistritos = "C:\\Users\\jcand\\OneDrive - Universidad Latina\\ulatina\\ingenieria en sistemas\\2026\\progra3\\Examen2\\distelec.txt";
 
    public static void main(String[] args) {
 
        System.out.println("ingrese numero de cedula: ");
 
        String cedula = sc.nextLine();
 
        try (BufferedReader bR = new BufferedReader(new FileReader(archivoPadron));
                BufferedReader bR2 = new BufferedReader(new FileReader(archivoDistritos))) {
 
            String linea;
 
            while ((linea = bR.readLine()) != null) {
 
                String[] datos = linea.split(",");
 
                if (datos[0].equals(cedula)) {
 
                    System.out.println("Los datos de la persona son:");
                    System.out.println("Cedula: " + datos[0].trim());
                    System.out.println("Nombre: " + datos[4].trim());
                    System.out.println("Apellidos: " + datos[5].trim() +" "+datos[6].trim());
                    System.out.println("cod. electoral: " + datos[1]);
                    String distrito; 
                    while ((distrito = bR2.readLine()) != null){
                        String[] datosDitrito = distrito.split(",");
                        if (datosDitrito[0].equals(datos[1])){
                            System.out.println("Provincia: " + datosDitrito[1]);
                            System.out.println("Canton: " + datosDitrito[2]);
                            System.out.println("Distrito: " +datosDitrito[3]);
                        }

                    }

                }
 
            }
 
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
 
    }
 
}