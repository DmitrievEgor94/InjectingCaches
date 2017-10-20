import caches.Cache;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Map;

public class Injector {

    private Map<String, Cache> cacheMap;

    public Injector(Map<String, Cache> cacheMap) {
        this.cacheMap = cacheMap;
    }

    void inject(Object ob) {
        Class obClass = ob.getClass();

        while (obClass != Object.class) {
            Field[] declaredFields = obClass.getDeclaredFields();

            AccessibleObject.setAccessible(declaredFields, true);

            for (Field declaredField : declaredFields) {
                InjectCache annotation = declaredField.getAnnotation(InjectCache.class);
                if (annotation == null) continue;

                Cache cache = cacheMap.get(annotation.name());

                if (cache == null) continue;

                try {
                    declaredField.set(ob, cache);
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
            obClass = obClass.getSuperclass();
        }

    }
}

