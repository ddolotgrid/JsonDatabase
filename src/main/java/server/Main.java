package server;

import server.database.JsonDatabase;
import server.database.JsonFileHandler;

public class Main {

    public static void main(String[] args) {
        JsonFileHandler fileHandler = new JsonFileHandler("db.json");
        JsonDatabase database = new JsonDatabase(fileHandler);
        Server server = new Server(database);
        server.start();
    }

}
