/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author jasga
 */
public class Persona {
    private String cedula;
    private String codigoElectoral;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    
 
        public Persona(String cedula,
                   String codigoElectoral,
                   String nombre,
                   String primerApellido,
                   String segundoApellido) {

        this.cedula = cedula;
        this.codigoElectoral = codigoElectoral;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }
    
        public String getCedula() {
        return cedula;
    }

    public String getCodigoElectoral() {
        return codigoElectoral;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }
    
}
