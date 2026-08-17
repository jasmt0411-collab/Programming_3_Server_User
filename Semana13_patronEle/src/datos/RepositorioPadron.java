/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.Persona;
import java.io.IOException;
/**
 *
 * @author jasga
 */
public interface RepositorioPadron {
    Persona buscarPorCedula(String cedula) throws IOException;
}
