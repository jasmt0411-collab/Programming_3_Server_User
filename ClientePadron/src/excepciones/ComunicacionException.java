/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author jasga
 */
public class ComunicacionException extends Exception{
    
    public ComunicacionException(String mensaje) {
        super(mensaje);
    }

    public ComunicacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
