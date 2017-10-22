package com.mycompany;

import com.mycompany.caches.Cache;
import com.mycompany.caches.InjectCache;

public class Library {

    @InjectCache(name = "books")
    private Cache books;

    public Cache getBooks() {
        return books;
    }
}
