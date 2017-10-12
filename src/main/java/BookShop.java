import java.util.List;
import java.util.Optional;

public class BookShop extends Library {

    private List<Double> prices;

    public BookShop(List<Double> prices) {
        this.prices = prices;
    }

    public BookShop() {
    }

    public Optional<List<Double>> getPrices() {
        return Optional.ofNullable(prices);
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }
}
