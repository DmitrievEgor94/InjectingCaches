package com.mycompany.caches;

public interface Cache {

    void put(int key, String value);

    String get(int key);

    String getFileNameWithData();
}
