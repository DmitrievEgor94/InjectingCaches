import caches.Cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FillerOfCache {
    static void fillCache(String fileName, Cache cache) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));

        int key = -1;
        String value = "";

        while (scanner.hasNext()) {
            String buffer = scanner.next();

            if (buffer.matches("\\d+")) {
                if (value != "") cache.put(key, value);

                value = "";
                key = Integer.parseInt(buffer);

            } else value += buffer + " ";

            if (!scanner.hasNext()) cache.put(key, value);
        }
    }
}
