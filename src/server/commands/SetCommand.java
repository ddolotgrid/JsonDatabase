package server.commands;

import com.google.gson.Gson;
import server.Database;
import server.Response;

public class SetCommand implements Command{
    private final Database db;
    private final String key;
    private final String value;

    public SetCommand(Database db, String key, String value) {
        this.db = db;
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        Gson json = new Gson();
        db.set(key,value);
        return json.toJson(new Response().setResponse("OK"),Response.class);
        }

}
