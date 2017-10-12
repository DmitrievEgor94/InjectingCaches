import caches.Cache;

import java.util.Optional;

public class Library {

    @InjectCache(name = "books")
    private Cache books;

    public Optional<Cache> getBooks() {
        return Optional.ofNullable(books);
    }
}
