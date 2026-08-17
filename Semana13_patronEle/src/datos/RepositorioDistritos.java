/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.DistritoElectoral;
import java.io.IOException;
/**
 *
 * @author jasga
 */
public interface RepositorioDistritos {
    DistritoElectoral buscarPorCodigo (String codigoElectoral) throws IOException;
}
