package util;

import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static String convertirAJson(Object objeto) {

        return gson.toJson(objeto);

    }
}