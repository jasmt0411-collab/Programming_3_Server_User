package service;

import dto.PersonaDTO;
import entities.DistritoElectoral;
import entities.Persona;
import repository.DistritoRepository;
import repository.PadronRepository;
import java.io.IOException;

public class PadronService {

    private final PadronRepository padronRepository;
    private final DistritoRepository distritoRepository;

    public PadronService() {

        this.padronRepository = new PadronRepository();
        this.distritoRepository = new DistritoRepository();
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