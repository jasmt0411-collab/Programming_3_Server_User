/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;
import comunicacion.ClienteComunicacion;
import comunicacion.ClienteHTTP;
import comunicacion.ClienteTCP;
import config.Configuracion;
import dto.PersonaDTO;
import excepciones.ComunicacionException;
/**
 *
 * @author jasga
 */
public class ServicioConsulta {
    
    public enum Protocolo { TCP, HTTP }

    private final Configuracion configuracion;

    public ServicioConsulta(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public PersonaDTO consultar(String cedula, Protocolo protocolo) throws ComunicacionException {

        ClienteComunicacion cliente = crearCliente(protocolo);

        return cliente.consultar(cedula);
    }

    private ClienteComunicacion crearCliente(Protocolo protocolo) {

        switch (protocolo) {
            case TCP:
                return new ClienteTCP(configuracion);
            case HTTP:
                return new ClienteHTTP(configuracion);
            default:
                throw new IllegalArgumentException("Protocolo no soportado: " + protocolo);
        }
    }
}
