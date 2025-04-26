package server.commands;

import com.google.gson.Gson;
import server.Database;
import server.Response;

import java.io.IOException;

public class ExitCommand implements Command{
    private final Database db;

    public ExitCommand(Database db) {
        this.db = db;
    }

    @Override
    public String execute(){
        return new Gson().toJson(
                new Response()
                        .setResponse("OK"),Response.class
        );

    }
}
