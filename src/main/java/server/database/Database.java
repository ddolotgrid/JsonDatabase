package server.database;

import com.google.gson.JsonElement;
import server.model.Response;

import java.util.List;

public interface Database {
    Response get(List<String> key);
    Response set(List<String> key, JsonElement value);
    Response delete(List<String> key);
}
