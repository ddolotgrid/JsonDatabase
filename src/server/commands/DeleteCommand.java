package server.commands;

import com.google.gson.Gson;
import server.Database;
import server.Response;

public class DeleteCommand implements Command{
    private final Database db;
    private final String key;
    private final GetCommand getCommand;


    public DeleteCommand(Database db, String key) {
        this.db = db;
        this.key = key;
        getCommand = new GetCommand(db,key);
    }

    @Override
    public String execute() {
        Gson json = new Gson();
        if(db.get(key).isEmpty())
        {
            return json.toJson(
                    new Response()
                            .setResponse("ERROR")
                            .setReason("No such key")
                    ,Response.class
            );
        }

        db.delete(key);
        return json.toJson(new Response().setResponse("OK"),Response.class);
    }
}
