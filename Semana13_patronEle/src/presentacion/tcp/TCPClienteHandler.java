/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.tcp;
import config.Configuracion;
import dto.ErrorDTO;
import dto.PersonaDTO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import logica.PadronService;
import util.JsonUtil;
import util.Validador;
/**
 *
 * @author jasga
 */
public class TCPClienteHandler implements Runnable {
    
    private Socket socket;
    private Configuracion configuracion;

    public TCPClienteHandler(Socket socket, Configuracion configuracion) {
        this.socket = socket;
        this.configuracion = configuracion;
    }

    @Override
    public void run() {

        try (
                BufferedReader entrada =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                PrintWriter salida =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);
                ) {

            try {

                atenderSolicitud(entrada, salida);

            } catch (Exception ex) {

                ex.printStackTrace();

                ErrorDTO error =
                        new ErrorDTO(
                                true,
                                500,
                                "Error interno del servidor.");

                salida.println(
                        JsonUtil.convertirAJson(error));

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            try {
                socket.close();
            } catch (Exception ex) {
            }

        }
    }

    private void atenderSolicitud(
            BufferedReader entrada,
            PrintWriter salida) throws Exception {

        String solicitud = entrada.readLine();

        if (solicitud == null || solicitud.isBlank()) {

            ErrorDTO error =
                    new ErrorDTO(
                            true,
                            400,
                            "Solicitud vacía.");

            salida.println(
                    JsonUtil.convertirAJson(error));

            return;
        }

        String[] partes =
                solicitud.split("\\|");

        if (partes.length != 2) {

            ErrorDTO error =
                    new ErrorDTO(
                            true,
                            400,
                            "Solicitud incorrecta.");

            salida.println(
                    JsonUtil.convertirAJson(error));

            return;
        }

        if (!partes[0].equalsIgnoreCase("GET")) {

            ErrorDTO error =
                    new ErrorDTO(
                            true,
                            400,
                            "Comando no soportado.");

            salida.println(
                    JsonUtil.convertirAJson(error));

            return;
        }

        String cedula = partes[1];

        if (!Validador.esCedulaValida(cedula)) {

            ErrorDTO error =
                    new ErrorDTO(
                            true,
                            400,
                            "Cédula inválida.");

            salida.println(
                    JsonUtil.convertirAJson(error));

            return;
        }

        PadronService service =
                new PadronService(configuracion);

        PersonaDTO persona =
                service.consultarPorCedula(cedula);

        if (persona == null) {

            ErrorDTO error =
                    new ErrorDTO(
                            true,
                            404,
                            "No se encontró la cédula.");

            salida.println(
                    JsonUtil.convertirAJson(error));

        } else {

            salida.println(
                    JsonUtil.convertirAJson(persona));
        }
    }
}