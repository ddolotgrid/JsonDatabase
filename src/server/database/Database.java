package server.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.model.Response;

import java.util.List;

public class Database {

    private final JsonObject storage;
    private final JsonFileHandler fileHandler;


    public Database(JsonFileHandler fileHandler) {
        this.fileHandler = fileHandler;
        storage = fileHandler.read();
    }


    public JsonObject get(List<String> key) {
        JsonElement current = storage;
        System.out.println(key);
        for (String k : key) {
            if (!current.isJsonObject() || !current.getAsJsonObject().has(k)) {
                return Response.error();
            }
            current = current.getAsJsonObject().get(k);
        }
        return Response.okWithValue(current);
    }

    public JsonObject set(List<String> keys, JsonElement value) {
        JsonObject current = storage;
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);

            JsonElement next = current.get(key);
            if (next != null && next.isJsonObject()) {
                current = next.getAsJsonObject();
            } else {
                JsonObject newObj = new JsonObject();
                current.add(key, newObj);
                current = newObj;
            }
        }
        String lastKey = keys.get(keys.size() - 1);
        current.add(lastKey, value);

        fileHandler.write(storage);
        return Response.ok();
    }


    public JsonObject delete(List<String> keys) {

        JsonObject current = storage;
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);

            if (current.has(key) && current.get(key).isJsonObject()) {
                current = current.getAsJsonObject(key);
            } else {
                return Response.error();
            }
        }

        String lastKey = keys.get(keys.size() - 1);
        if (current.has(lastKey)) {
            current.remove(lastKey);
            fileHandler.write(storage);
            return Response.ok();
        } else {
            return Response.error();
        }
    }

}









