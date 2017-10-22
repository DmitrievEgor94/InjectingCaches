package com.mycompany;

import com.mycompany.caches.Cache;
import com.mycompany.caches.InjectCache;

public class Paper {

    @InjectCache(name = "languagesOfProgramming")
    private Cache languagesOfProgramming;

    public Cache getLanguagesOfProgramming() {
        return languagesOfProgramming;
    }
}
