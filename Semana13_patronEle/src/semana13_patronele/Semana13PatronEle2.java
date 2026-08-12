/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana13_patronele;
import dto.PersonaDTO;

import service.PadronService;

/**
 *esto es un test 
 * @author jcand
 */
public class Semana13PatronEle2 {

    public static void main(String[] args) {
       

try {
           

PadronService service
                    = new PadronService();
           

PersonaDTO persona
                    = service.consultarPorCedula("115550555");
           

if (persona != null) {
               

System.out.println(persona.getNombre());

                System.out.println(persona.getProvincia());
             

} else {


                
               

System.out.println("Persona no encontrada");

            }
        

} catch (Exception ex) {
           

System.out.println(ex.getMessage());
        
    


}

}

}
