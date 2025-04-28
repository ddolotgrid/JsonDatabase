package client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler{

    private static final String path = System.getProperty("user.dir") + "/src/client/data/";

    public String getRequest() throws IOException {
        Gson gson = new Gson();
        String req;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("/Users/ddolot/Desktop/JSON Database with Java/JSON Database with Java/JSON Database with Java/JSON Database with Java/task/src/client/data/db.json"))) {
            return gson.fromJson(reader, Object.class).toString();

        }
    }

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();

        try {
            fileHandler.getRequest();
        } catch (IOException e) {
            System.out.println("no file in " + Paths.get(path + "db.json"));
        }
    }

}
