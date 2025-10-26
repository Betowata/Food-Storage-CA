// Import date ortime classes from Java library
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FoodItem {

    // Properties
    // names of the food
    private String name;

    // weight of the food in grams
    private int weightGrams;

    // date for the food item and when expires
    private LocalDate bestBefore;

    //  time when added into storage
    private LocalDateTime timePlaced;

    //  make the date or time display neat and readable
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructor
    // When a new FoodItem is created, it will show the info

    public FoodItem(String name, int weightGrams, LocalDate bestBefore, LocalDateTime timePlaced) {
        this.name = name;
        this.weightGrams = weightGrams;
        this.bestBefore = bestBefore;
        this.timePlaced = timePlaced;
    }

    // Getter methods
    public String getName() { return name; }
    public int getWeightGrams() { return weightGrams; }
    public LocalDate getBestBefore() { return bestBefore; }
    public LocalDateTime getTimePlaced() { return timePlaced; }

    // toString() method
    // This method will format all the details into a line of text.
    // It is automatically called when we print a FoodItem object.
    @Override
    public String toString() {
        return String.format(
            "Name: %s | Weight: %d g | BestBefore: %s | TimePlaced: %s",
            name, weightGrams,
            bestBefore.format(DATE_FMT),
            timePlaced.format(TIME_FMT)
        );
    }
}
