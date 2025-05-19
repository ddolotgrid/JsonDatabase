package server.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonFileHandler implements FileHandler {
    private final ReadWriteLock reentrantLock;
    private static final Gson gson = new Gson();
    private static final String FILEPATH = System.getProperty("user.dir") + "/src/main/java/server/data/";
    private final String fileName;
    private static final Logger LOGGER = Logger.getLogger(JsonFileHandler.class.getName());

    public JsonFileHandler(String fileName) {
        this.reentrantLock = new ReentrantReadWriteLock();
        this.fileName = fileName;
    }

    public JsonObject read() {
        reentrantLock.readLock().lock();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILEPATH+fileName))) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Failed to read data from file" + e.getMessage());
        } finally {
            reentrantLock.readLock().unlock();
        }
        return new JsonObject();
    }

    public void write(JsonObject storage) {
        reentrantLock.writeLock().lock();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILEPATH+fileName))) {
            gson.toJson(storage,writer);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Failed to write data to file" + e.getMessage());
        } finally {
            reentrantLock.writeLock().unlock();
        }
    }
}
