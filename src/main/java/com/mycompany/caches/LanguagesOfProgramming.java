package com.mycompany.caches;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "languagesOfProgramming")
public class LanguagesOfProgramming implements Cache {

    private Map<Integer, String> languagesOfProgramming;

    public LanguagesOfProgramming() {
        languagesOfProgramming = new HashMap<>();
    }

    public void put(int key, String value) {
        languagesOfProgramming.put(key, value);
    }

    public String get(int key) {
        return languagesOfProgramming.get(key);
    }

    public String getFileNameWithData() {
        return "fileWithLanguagesOfProgramming.txt";
    }
}