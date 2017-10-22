package com.mycompany.caches;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "clients")
public class Clients implements Cache {

    public Map<Integer, String> clients;

    public Clients() {
        clients = new HashMap<>();
    }

    public void put(int key, String value) {
        clients.put(key, value);
    }

    public String get(int key) {
        return clients.get(key);
    }

    public String getFileNameWithData() {
        return "fileWithClients.txt";
    }

}
