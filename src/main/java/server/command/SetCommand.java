package server.command;

import com.google.gson.JsonElement;
import server.database.Database;
import server.database.JsonDatabase;
import server.model.Response;
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
    public Response execute() {
        List<String> keys = parseKey(key);
        return db.set(keys, value);
    }
}
