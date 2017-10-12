package caches;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "books")
public class Books implements Cache {

    private Map<Integer, String> clients;

    public Books() {
        clients = new HashMap<Integer, String>();
    }

    public void put(int key, String value) {
        clients.put(key, value);
    }

    public String get(int key) {
        return clients.get(key);
    }

}