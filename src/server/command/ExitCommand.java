package server.command;

import server.model.Response;

public class ExitCommand implements Command {

    @Override
    public String execute() {
        return Response.ok().toString();

    }
}
