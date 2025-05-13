package server.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public interface IDatabase {
    JsonObject get(List<String> key);
    JsonObject set(List<String> key, JsonElement value);
    JsonObject delete(List<String> key);
}
