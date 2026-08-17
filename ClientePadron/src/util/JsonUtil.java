/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dto.ErrorDTO;
import dto.PersonaDTO;
/**
 *
 * @author jasga
 */
public class JsonUtil {
    private static final Gson gson = new Gson();

    public static PersonaDTO aPersonaDTO(String json) throws JsonSyntaxException {
        return gson.fromJson(json, PersonaDTO.class);
    }

    public static ErrorDTO aErrorDTO(String json) throws JsonSyntaxException {
        return gson.fromJson(json, ErrorDTO.class);
    }

    public static boolean esRespuestaDeError(String json) {
        try {
            var objeto = JsonParser.parseString(json).getAsJsonObject();
            return objeto.has("error") && objeto.get("error").getAsBoolean();
        } catch (Exception ex) {
            return false;
        }
    } 
}
