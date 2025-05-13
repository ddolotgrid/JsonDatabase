package server.command;

import server.database.Database;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SetCommand implements Command {
    private final Database db;
    private final JsonElement key;
    private final JsonElement value;


    public SetCommand(Database db, JsonElement key, JsonElement value) {
        this.db = db;
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        List<String> keys = parseKey(key);
        return db.set(keys, value).toString();

    }


}
