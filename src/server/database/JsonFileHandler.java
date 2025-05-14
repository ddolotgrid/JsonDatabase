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

public class JsonFileHandler implements IJsonFileHandler{
    private final ReadWriteLock reentrantLock;
    private final Gson gson;
    private static final String filePath = System.getProperty("user.dir") + "/src/server/data/";
    private final String fileName;
    private static final Logger logger = Logger.getLogger(JsonFileHandler.class.getName());

    public JsonFileHandler(String fileName) {
        this.reentrantLock = new ReentrantReadWriteLock();
        this.gson = new Gson();
        this.fileName = fileName;
    }

    public JsonObject read() {
        reentrantLock.readLock().lock();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath+fileName))) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Failed to read data from file" + e.getMessage());
        } finally {
            reentrantLock.readLock().unlock();
        }
        return new JsonObject();
    }

    public void write(JsonObject storage) {
        reentrantLock.readLock().lock();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath+fileName))) {
            gson.toJson(storage,writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Failed to write data to file" + e.getMessage());
        } finally {
            reentrantLock.readLock().unlock();
        }
    }
}
