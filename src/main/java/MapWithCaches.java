import caches.Cache;
import caches.CacheDeclaration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MapWithCaches {

    static private String packageWithCaches = "caches";
    static private String pathToJarFile = "out/artifacts/InjectingCaches_jar/InjectingCaches.jar";

    private Map<String, Cache> cacheList;

    {
        cacheList = new HashMap<>();

        try {
            updateCaches();
        } catch (IOException e) {
            System.out.println("Wrong name of package with caches or wrong path to JarFile!");
        }
    }

    public void updateCaches() throws IOException {
        cacheList.clear();

        String packageWithCachesPath = packageWithCaches.replace(".", "/");

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

        URL resource = currentClassLoader.getResource(packageWithCachesPath);

        List<String> cachesNames = getListOfCachesNames();

        try {
            for (String cache : cachesNames) {
                String specifiedCachePackage = cache.substring(0, cache.lastIndexOf("."));
                specifiedCachePackage = specifiedCachePackage.replace("/", ".");

                Class cacheClass = Class.forName(specifiedCachePackage);
                CacheDeclaration cacheDeclarationAnnotation = null;

                if (cacheClass != null)
                    cacheDeclarationAnnotation = (CacheDeclaration) cacheClass.getAnnotation(CacheDeclaration.class);

                if (cacheDeclarationAnnotation != null) {
                    Cache ob = (Cache) cacheClass.newInstance();

                    FillerOfCache.fillCache(resource.getPath() + "/" + ob.getFileNameWithData(), ob);
                    cacheList.put(cacheDeclarationAnnotation.name(), ob);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | NoClassDefFoundError | IllegalAccessException e) {
            System.out.println(e);
        }

    }

    public List<String> getListOfCachesNames() throws IOException {
        JarFile jarFile = new JarFile(pathToJarFile);
        Enumeration<JarEntry> entries = jarFile.entries();
        List<String> cachesNames = new ArrayList<>();

        while (entries.hasMoreElements()) {
            String cacheName = entries.nextElement().getName();
            if (cacheName.contains(packageWithCaches) && cacheName.contains(".class"))
                cachesNames.add(cacheName);
        }

        return cachesNames;
    }

    public Map<String, Cache> getCacheList() {
        return cacheList;
    }
}
