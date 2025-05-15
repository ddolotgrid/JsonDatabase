package server.command;

import com.google.gson.JsonElement;
import server.database.Database;
import server.database.JsonDatabase;
import server.model.Response;
import java.util.List;

public class DeleteCommand implements Command {
    private final Database db;
    private final JsonElement key;

    public DeleteCommand(Database db, JsonElement key) {
        this.db = db;
        this.key = key;
    }

    @Override
    public Response execute() {
        List<String> keys = parseKey(key);
        return db.delete(keys);
        }
    }

