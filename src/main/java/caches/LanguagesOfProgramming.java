package caches;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "languagesOfProgramming")
public class LanguagesOfProgramming implements Cache {

    private Map<Integer, String> clients;

    public LanguagesOfProgramming() {
        clients = new HashMap<>();
    }

    public void put(int key, String value) {
        clients.put(key, value);
    }

    public String get(int key) {
        return clients.get(key);
    }

}