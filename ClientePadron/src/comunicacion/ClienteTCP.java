/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;
import config.Configuracion;
import dto.ErrorDTO;
import dto.PersonaDTO;
import excepciones.ComunicacionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import util.JsonUtil;
/**
 *
 * @author jasga
 */
public class ClienteTCP implements ClienteComunicacion {
    
    private final Configuracion configuracion;

    public ClienteTCP(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public PersonaDTO consultar(String cedula) throws ComunicacionException {

        try (Socket socket = new Socket()) {

            socket.connect(
                    new InetSocketAddress(
                            configuracion.getServidorHost(),
                            configuracion.getPuertoTcp()),
                    configuracion.getTimeoutMs());

            socket.setSoTimeout(configuracion.getTimeoutMs());

            try (
                    PrintWriter salida =
                            new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader entrada =
                            new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));
                    ) {

                salida.println("GET|" + cedula);

                String respuesta = entrada.readLine();

                return procesarRespuesta(respuesta);
            }

        } catch (SocketTimeoutException ex) {

            throw new ComunicacionException(
                    "El servidor tardó demasiado en responder (tiempo de espera agotado).", ex);

        } catch (UnknownHostException ex) {

            throw new ComunicacionException(
                    "No se pudo resolver la dirección del servidor.", ex);

        } catch (IOException ex) {

            throw new ComunicacionException(
                    "No se pudo conectar con el servidor por TCP. "
                            + "Verifique que esté encendido y el puerto sea correcto.", ex);

        }
    }

    private PersonaDTO procesarRespuesta(String respuesta) throws ComunicacionException {

        if (respuesta == null || respuesta.isBlank()) {
            throw new ComunicacionException("El servidor no envió ninguna respuesta.");
        }

        try {

            if (JsonUtil.esRespuestaDeError(respuesta)) {

                ErrorDTO error = JsonUtil.aErrorDTO(respuesta);
                throw new ComunicacionException(error.getMensaje());
            }

            return JsonUtil.aPersonaDTO(respuesta);

        } catch (com.google.gson.JsonSyntaxException ex) {

            throw new ComunicacionException(
                    "El servidor envió una respuesta que no se pudo interpretar (JSON inválido).", ex);

        }
    } 
   
}
