package server.database;

import com.google.gson.JsonObject;

public interface FileHandler {
    JsonObject read();
    void write(JsonObject data);
}
