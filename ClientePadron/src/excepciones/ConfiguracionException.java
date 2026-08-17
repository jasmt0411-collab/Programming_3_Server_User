/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author jasga
 */
public class ConfiguracionException extends Exception{
    public ConfiguracionException(String mensaje) {
        super(mensaje);
    }

    public ConfiguracionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
