import caches.Cache;
import caches.CacheDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Injector {

    private List<Cache> cacheList;

    public Injector(String packageWithCaches, String packageWithData) throws FileNotFoundException {
        cacheList = new ArrayList<>();
        updateCaches(packageWithCaches, packageWithData);
    }

    public void updateCaches(String packageWithCaches, String packageWithData) throws FileNotFoundException {
        cacheList.clear();

        packageWithCaches = packageWithCaches.replace(".", "/");
        packageWithData = packageWithData.replace(".", "\\");

        URL resource = Thread.currentThread().getContextClassLoader().getResource(packageWithCaches);

        File file = new File(resource.getFile());

        String[] caches = Optional.of(file.list()).orElse(new String[0]);

        try {
            for (String cache : caches) {
                String className = cache.substring(0, cache.indexOf('.'));

                Class cacheClass = Class.forName(packageWithCaches + "." + className);

                if (cacheClass.getAnnotation(CacheDeclaration.class) != null) {
                    Cache ob = (Cache) cacheClass.newInstance();
                    FillerOfCache.fillCache(packageWithData + "\\" + className.toLowerCase() + ".txt", ob);
                    cacheList.add(ob);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoClassDefFoundError e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    void inject(Object ob) {
        Class obClass = ob.getClass();

        while (obClass != Object.class) {
            Field[] declaredFields = obClass.getDeclaredFields();

            AccessibleObject.setAccessible(declaredFields, true);

            for (Field declaredField : declaredFields) {
                InjectCache annotation = declaredField.getAnnotation(InjectCache.class);
                if (annotation == null) continue;

                for (Cache cache : cacheList) {
                    if (cache.getClass().getAnnotation(CacheDeclaration.class).name().equals(annotation.name()))
                        try {
                            declaredField.set(ob, cache);
                        } catch (IllegalAccessException e) {
                            System.out.println("Field" + declaredField + "in class" + obClass + "is private!");
                            e.printStackTrace();
                        }
                }
            }

            obClass = obClass.getSuperclass();
        }
    }

    public static void testInjecting() {
        Injector injector;

        try {
            injector = new Injector("caches", "src.main.resources");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        /*
        BookShop extends library that has private field with annotation.
         */
        BookShop bookShop = new BookShop();
        Hotel hotel = new Hotel();
        Paper paper = new Paper();

        injector.inject(bookShop);
        injector.inject(hotel);
        injector.inject(paper);

        if (bookShop.getBooks().isPresent())
            System.out.println(bookShop.getBooks().get().get(1));

        if (hotel.getClients().isPresent())
            System.out.println(hotel.getClients().get().get(2));

        if (paper.getLanguagesOfProgramming().isPresent())
            System.out.println(paper.getLanguagesOfProgramming().get().get(3));
    }

    public static void main(String[] args) throws IllegalAccessException {
        testInjecting();
    }
}
