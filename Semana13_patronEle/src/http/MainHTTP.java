package semana13_patronele;

import http.HttpServerPadron;

public class MainHTTP {

    public static void main(String[] args) {

        HttpServerPadron servidor =
                new HttpServerPadron();

        servidor.iniciar();

    }
}