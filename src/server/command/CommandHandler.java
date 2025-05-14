package server.command;

import server.Server;
import server.database.Database;
import server.model.ClientRequest;
import com.google.gson.JsonElement;
import server.model.Response;

public class CommandHandler {

    private final Database db;
    private final Server server;

    public CommandHandler(Database db, Server server) {
        this.db = db;
        this.server = server;
    }

    public Response executeRequest(ClientRequest request) {
        JsonElement key = request.getKey();
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
                server.shutdown();
                return Response.OK;
            default:
                return Response.EMPTY;
        }
        return command.execute();
    }
}





