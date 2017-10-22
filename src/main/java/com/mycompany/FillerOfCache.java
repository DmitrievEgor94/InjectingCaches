package com.mycompany;

import com.mycompany.caches.Cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FillerOfCache {
    static void fillCache(String fileName, Cache cache) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter(",");

        while (scanner.hasNext()) {
            String currentStringInFile = scanner.nextLine();

            int key = Integer.parseInt(currentStringInFile.split(",")[0]);

            String value = currentStringInFile.split(",")[1];

            cache.put(key, value);
        }
    }
}
