package server.database;

import com.google.gson.JsonObject;

public interface IJsonFileHandler {
    JsonObject read();
    void write(JsonObject data);
}
