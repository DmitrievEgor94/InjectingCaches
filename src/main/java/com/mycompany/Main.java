package com.mycompany;

import java.io.IOException;

public class Main {
    private static void testInjecting() {

        MapWithCaches mapWithCaches;

        try {
            mapWithCaches = new MapWithCaches();
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        Injector injector = new Injector(mapWithCaches.getCacheList());

        /*
        BookShop extends library that has private field with annotation.
         */
        BookShop bookShop = new BookShop();
        Hotel hotel = new Hotel();
        Paper paper = new Paper();

        try {
            injector.inject(bookShop);
            System.out.println(bookShop.getBooks().get(1));

            injector.inject(hotel);
            System.out.println(hotel.getClients().get(2));

            injector.inject(paper);
            System.out.println(paper.getLanguagesOfProgramming().get(3));

        } catch (NotFoundCacheException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        testInjecting();
    }
}
