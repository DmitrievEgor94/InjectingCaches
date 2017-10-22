package com.mycompany;

import com.mycompany.caches.Cache;
import com.mycompany.caches.CacheDeclaration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapWithCaches {

    static private String packageWithCaches = "com.mycompany.caches";

    private Map<String, Cache> cacheList;

    public MapWithCaches() throws IOException {
        cacheList = new HashMap<>();
        try {
            updateCaches();
        } catch (IOException e) {
            throw new IOException("Wrong name of package with caches!");
        }
    }

    private void updateCaches() throws IOException {
        cacheList.clear();

        String packageWithCachesPath = packageWithCaches.replace(".", "/");

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

        URL cachesPath = currentClassLoader.getResource(packageWithCachesPath);

        String directoryWithCaches = cachesPath.getPath();

        File file = new File(cachesPath.getFile());

        String[] cachesNames = Optional.of(file.list()).orElse(new String[0]);

        try {
            for (String cache : cachesNames) {
                if (cache.contains(".class")) {
                    String cacheNameWithoutClass = cache.substring(0, cache.lastIndexOf("."));

                    Class cacheClass = Class.forName(packageWithCaches + "." + cacheNameWithoutClass);

                    CacheDeclaration cacheDeclarationAnnotation = (CacheDeclaration) cacheClass.getAnnotation(CacheDeclaration.class);

                    if (cacheDeclarationAnnotation != null) {
                        Cache ob = (Cache) cacheClass.newInstance();

                        FillerOfCache.fillCache(directoryWithCaches + "/" + ob.getFileNameWithData(), ob);
                        cacheList.put(cacheDeclarationAnnotation.name(), ob);
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | NoClassDefFoundError | IllegalAccessException e) {
            System.out.println(e);
        }
    }


    public Map<String, Cache> getCacheList() {
        return cacheList;
    }
}
