package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import config.Config;
import dto.ErrorDTO;
import dto.PersonaDTO;
import service.PadronService;
import util.JsonUtil;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpServerPadron {

    public void iniciar() {

        try {

            HttpServer server = HttpServer.create(
                    new InetSocketAddress(Config.HTTP_PORT),
                    0);

            server.createContext(
                    "/padron",
                    this::procesarConsulta);

            server.setExecutor(null);

            server.start();

            System.out.println(
                    "Servidor HTTP iniciado en puerto "
                            + Config.HTTP_PORT);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private void procesarConsulta(HttpExchange exchange) {

        try {

            if (!exchange.getRequestMethod().equals("GET")) {

                ErrorDTO error =
                        new ErrorDTO(
                                true,
                                405,
                                "Método no permitido.");

                responder(
                        exchange,
                        405,
                        JsonUtil.convertirAJson(error));

                return;
            }

            String ruta =
                    exchange.getRequestURI().getPath();

            String[] partes =
                    ruta.split("/");

            if (partes.length != 3) {

                ErrorDTO error =
                        new ErrorDTO(
                                true,
                                404,
                                "Ruta inválida.");

                responder(
                        exchange,
                        404,
                        JsonUtil.convertirAJson(error));

                return;
            }

            String cedula = partes[2];

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

                responder(
                        exchange,
                        404,
                        JsonUtil.convertirAJson(error));

            } else {

                responder(
                        exchange,
                        200,
                        JsonUtil.convertirAJson(persona));
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private void responder(
            HttpExchange exchange,
            int codigo,
            String json) throws Exception {

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json");

        exchange.sendResponseHeaders(
                codigo,
                json.getBytes().length);

        OutputStream os =
                exchange.getResponseBody();

        os.write(json.getBytes());

        os.close();
    }
}