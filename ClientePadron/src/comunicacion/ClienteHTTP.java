/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;
import config.Configuracion;
import dto.ErrorDTO;
import dto.PersonaDTO;
import excepciones.ComunicacionException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import util.JsonUtil;
/**
 *
 * @author jasga
 */
public class ClienteHTTP implements ClienteComunicacion{
    
    private final Configuracion configuracion;

    public ClienteHTTP(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public PersonaDTO consultar(String cedula) throws ComunicacionException {

        String url = "http://" + configuracion.getServidorHost()
                + ":" + configuracion.getPuertoHttp()
                + "/padron/" + cedula;

        HttpClient cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(configuracion.getTimeoutMs()))
                .build();

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(configuracion.getTimeoutMs()))
                .GET()
                .build();

        try {

            HttpResponse<String> respuesta =
                    cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            return procesarRespuesta(respuesta.body());

        } catch (HttpTimeoutException ex) {

            throw new ComunicacionException(
                    "El servidor tardó demasiado en responder (tiempo de espera agotado).", ex);

        } catch (ConnectException ex) {

            throw new ComunicacionException(
                    "No se pudo conectar con el servidor por HTTP. "
                            + "Verifique que esté encendido y el puerto sea correcto.", ex);

        } catch (IOException | InterruptedException ex) {

            throw new ComunicacionException(
                    "Error de comunicación con el servidor.", ex);

        }
    }

    private PersonaDTO procesarRespuesta(String cuerpo) throws ComunicacionException {

        if (cuerpo == null || cuerpo.isBlank()) {
            throw new ComunicacionException("El servidor no envió ninguna respuesta.");
        }

        try {

            if (JsonUtil.esRespuestaDeError(cuerpo)) {

                ErrorDTO error = JsonUtil.aErrorDTO(cuerpo);
                throw new ComunicacionException(error.getMensaje());
            }

            return JsonUtil.aPersonaDTO(cuerpo);

        } catch (com.google.gson.JsonSyntaxException ex) {

            throw new ComunicacionException(
                    "El servidor envió una respuesta que no se pudo interpretar (JSON inválido).", ex);

        }
    }
    
}
