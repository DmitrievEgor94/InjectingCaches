import caches.Cache;
import caches.CacheDeclaration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapWithCaches {

    static private String packageWithCaches = "caches";

    private Map<String, Cache> cacheList;

    {
        cacheList = new HashMap<>();

        try {
            updateCaches();
        } catch (IOException e) {
            System.out.println("Wrong name of package with caches!");
        }
    }

    public void updateCaches() throws IOException {
        cacheList.clear();

        String packageWithCachesPath = packageWithCaches.replace(".", "/");

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

        URL cachesPath = currentClassLoader.getResource(packageWithCachesPath);

        String directoryWithCaches = cachesPath.getPath();
        String rootDirectory = directoryWithCaches.substring(0, directoryWithCaches.indexOf(packageWithCachesPath));

        File file = new File(cachesPath.getFile());

        String[] cachesNames = Optional.of(file.list()).orElse(new String[0]);

        try {
            for (String cache : cachesNames) {
                String cacheNameWithoutClass = cache.substring(0, cache.lastIndexOf("."));

                Class cacheClass = Class.forName(packageWithCaches + "." + cacheNameWithoutClass);
                CacheDeclaration cacheDeclarationAnnotation = null;

                if (cacheClass != null)
                    cacheDeclarationAnnotation = (CacheDeclaration) cacheClass.getAnnotation(CacheDeclaration.class);

                if (cacheDeclarationAnnotation != null) {
                    Cache ob = (Cache) cacheClass.newInstance();

                    FillerOfCache.fillCache(rootDirectory + "/" + ob.getFileNameWithData(), ob);
                    cacheList.put(cacheDeclarationAnnotation.name(), ob);
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
