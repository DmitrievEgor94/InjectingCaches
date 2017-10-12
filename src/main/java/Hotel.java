import caches.Cache;

import java.util.Optional;

public class Hotel {
    @InjectCache(name="clients")
    Cache clients;

    public Optional<Cache> getClients() {
        return Optional.ofNullable(clients);
    }
}
