package server.commands;

import com.google.gson.Gson;
import server.Database;
import server.Response;

public class GetCommand implements Command {
    private final Database db;
    private final String key;

    public GetCommand(Database db,String key) {
        this.key = key;
        this.db = db;
    }

    @Override
    public String execute() {
        Gson json = new Gson();
        String value = db.get(key);
        if ("".equals(value)) {
            return json.toJson(
                    Response.error("No such key"),Response.class
            );
        }
        return json.toJson(
                Response.okWithValue(value)
                ,Response.class
        );
    }
    }

