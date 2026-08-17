/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author jasga
 */
public class ErrorDTO {
    private boolean error;
    private int codigo;
    private String mensaje;

    public boolean isError() { return error; }
    public int getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }
}
