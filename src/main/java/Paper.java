import caches.Cache;

import java.util.Optional;

public class Paper {

    @InjectCache(name = "languagesOfProgramming")
    private Cache languagesOfProgramming;

    public Optional<Cache> getLanguagesOfProgramming() {
        return Optional.ofNullable(languagesOfProgramming);
    }
}
