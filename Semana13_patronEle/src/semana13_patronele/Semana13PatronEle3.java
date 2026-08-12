/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana13_patronele;

import dto.PersonaDTO;
import service.PadronService;
import util.JsonUtil;

/**
 * otro test
 *
 * @author jcand
 */
public class Semana13PatronEle3 {

    public static void main(String[] args) {
        try {
            PadronService service
                    = new PadronService();
            PersonaDTO persona
                    = service.consultarPorCedula("115550555");
            if (persona != null) {
                String json
                        = JsonUtil.convertirAJson(persona);
                System.out.println(json);
            } else {
                System.out.println("Persona no encontrada");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
