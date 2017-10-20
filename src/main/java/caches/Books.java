package caches;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "books")
public class Books implements Cache {

    private Map<Integer, String> books;

    public Books() {
        books = new HashMap<>();
    }

    public void put(int key, String value) {
        books.put(key, value);
    }

    public String get(int key) {
        return books.get(key);
    }

    public String getFileNameWithData() {
        return "fileWithBooks.txt";
    }

}