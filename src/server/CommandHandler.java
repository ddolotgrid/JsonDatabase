package server;

import server.command.*;
import server.database.Database;
import server.model.ClientRequest;
import com.google.gson.JsonElement;


public class CommandHandler {

    private final Database db;
    private final Server server;

    public CommandHandler(Database db, Server server) {
        this.db = db;
        this.server = server;
    }

    public String executeRequest(ClientRequest request) {
        JsonElement key = request.getKey();
        System.out.println("Executing request: " + request.getType());

        Command command;

        switch (request.getType().toLowerCase()) {
            case "get":
                command = new GetCommand(db, key);
                break;
            case "set":
                command = new SetCommand(db, key, request.getValue());
                break;
            case "delete":
                command = new DeleteCommand(db, key);
                break;
            case "exit":
                command = new ExitCommand();
                String response = command.execute();
                server.shutdown();
                return response;
            default:
                throw new RuntimeException("Unknown command type: " + request.getType());
        }

        return command.execute();
    }

}





