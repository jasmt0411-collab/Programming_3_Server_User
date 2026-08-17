/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author jasga
 */
public class Validador {
        public static boolean esCedulaValida(String cedula) {

        if (cedula == null) {
            return false;
        }

        String limpia = cedula.trim();

        if (limpia.isEmpty()) {
            return false;
        }

        return limpia.chars().allMatch(Character::isDigit);
    }
}
