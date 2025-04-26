package server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Database {
    private final Map<String,String> storage;

    public Database() {
        storage = new HashMap<>();
    }

    public String get(String key){
        return storage.getOrDefault(key,
                ""
                );
    }

    public void set(String key, String value){
        storage.put(key,value);
    }

    public void delete(String key){
        storage.remove(key);
    }

}

