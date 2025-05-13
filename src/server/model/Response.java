package server.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Response {

    public static JsonObject ok() {
        JsonObject response = new JsonObject();
        response.addProperty("response","OK");
        return response;
    }

    public static JsonObject okWithValue(JsonElement value) {
        JsonObject response = new JsonObject();
        response.addProperty("response", "OK");
        response.add("value", value);
        return response;
    }

    public static JsonObject error() {
        JsonObject response = new JsonObject();
        response.addProperty("response", "ERROR");
        response.addProperty("reason", "No such key");
        return response;
    }
}
