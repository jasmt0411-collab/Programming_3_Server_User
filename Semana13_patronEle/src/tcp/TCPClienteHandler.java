package tcp;

import dto.ErrorDTO;
import dto.PersonaDTO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import service.PadronService;
import util.JsonUtil;

public class TCPClienteHandler implements Runnable {

    private Socket socket;

    public TCPClienteHandler(Socket socket) {
        this.socket = socket;
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

            PadronService service =
                    new PadronService();

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

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            try {
                socket.close();
            } catch (Exception ex) {
            }

        }
    }
}