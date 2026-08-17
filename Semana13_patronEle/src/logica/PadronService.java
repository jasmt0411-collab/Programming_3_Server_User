/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;
import config.Configuracion;
import dto.PersonaDTO;
import entidades.DistritoElectoral;
import entidades.Persona;
import datos.DistritoRepository;
import datos.PadronRepository;
import datos.RepositorioDistritos;
import datos.RepositorioPadron;
import java.io.IOException;
/**
 *
 * @author jasga
 */
public class PadronService {
    private final RepositorioPadron padronRepository;
    private final RepositorioDistritos distritoRepository;
    
    public PadronService(Configuracion configuracion) {
        this.padronRepository = new PadronRepository(configuracion);
        this.distritoRepository = new DistritoRepository(configuracion);
    }

    public PersonaDTO consultarPorCedula(String cedula)
            throws IOException {

        Persona persona =
                padronRepository.buscarPorCedula(cedula);

        if (persona == null) {
            return null;
        }

        DistritoElectoral distrito =
                distritoRepository.buscarPorCodigo(
                        persona.getCodigoElectoral());

        PersonaDTO dto = new PersonaDTO();

        dto.setCedula(persona.getCedula());
        dto.setNombre(persona.getNombre());
        dto.setPrimerApellido(persona.getPrimerApellido());
        dto.setSegundoApellido(persona.getSegundoApellido());
        dto.setCodigoElectoral(persona.getCodigoElectoral());

        if (distrito != null) {
            dto.setProvincia(distrito.getProvincia());
            dto.setCanton(distrito.getCanton());
            dto.setDistrito(distrito.getDistrito());
        }
        return dto;
    }
    
}
