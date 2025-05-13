package server;

import server.database.Database;
import server.database.JsonFileHandler;

public class Main {

    public static void main(String[] args) {
        JsonFileHandler fileHandler = new JsonFileHandler("db.json");
        Database database = new Database(fileHandler);
        Server server = new Server(database);
        System.out.println("Server started!");
        server.start();
    }

}
