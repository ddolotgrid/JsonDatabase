package server;

import java.util.Arrays;

public class Database {
    private final String[] storage = new String[1000];

    public Database() {
        Arrays.fill(storage,"");
    }

    public String get(int index){
        return storage[index];
    }

    public void set(int index, String value){
        storage[index] = value;
    }

    public void delete(int index){
        storage[index] = "";
    }

}

