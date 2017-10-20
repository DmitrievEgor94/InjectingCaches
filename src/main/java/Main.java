public class Main {
    public static void testInjecting() {
        MapWithCaches mapWithCaches = new MapWithCaches();
        Injector injector = new Injector(mapWithCaches.getCacheList());

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
