package server.command;

import server.Server;
import server.model.Response;

public class ExitCommand implements Command {

    private final Server server;

    public ExitCommand(Server server) {
        this.server = server;
    }

    @Override
    public Response execute() {
        server.shutdown();
        return Response.OK;
    }
}
