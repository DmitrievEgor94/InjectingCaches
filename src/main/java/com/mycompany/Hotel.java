package com.mycompany;

import com.mycompany.caches.Cache;
import com.mycompany.caches.InjectCache;

public class Hotel {
    @InjectCache(name = "clients")
    Cache clients;

    public Cache getClients() {
        return clients;
    }
}
