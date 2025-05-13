package server.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public interface Command {
    String execute();

    default List<String> parseKey(JsonElement key){
        List<String> keys = new ArrayList<>();

        if (key.isJsonPrimitive())  {
            keys.add(key.getAsString());
            return keys;
        }

        for(JsonElement k : key.getAsJsonArray()){
            keys.add(k.getAsString());
            System.out.println(k.getAsString());
        }
        return keys;
    }
}
