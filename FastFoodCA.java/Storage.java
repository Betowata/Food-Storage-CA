
 //This one will Model the rectangular Storage Unit.
 //Using a Deque so we can add and remove items
 //from the front or the end side.



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;


 // Two modes are supported:
 // STACK_FRONT_BOTH: add/remove from the same front side, which is LIFO behavior
 // ADD_FRONT_REMOVE_OPP: add from front, remove from opposite side whihc is FIFO behavior
public class Storage {

    // ENUM to represent how the storage behaves
    public enum Mode {
        STACK_FRONT_BOTH,    // add and remove front
        ADD_FRONT_REMOVE_OPP // add front and remove opposite 
    }

    private final int capacity;        // maximum trays allowed, 8 of them
    private final Deque<FoodItem> deque; // stores all the food trays
    private Mode mode;                 // current operation mode

    //  Constructor
    public Storage(int capacity, Mode initialMode) {
        this.capacity = capacity;
        this.deque = new ArrayDeque<>(capacity);
        this.mode = initialMode;
    }

    // Getters and Setters for the mode
    public void setMode(Mode m) { this.mode = m; }
    public Mode getMode() { return mode; }

    // Helper methods to check if storage is full or empty
    public boolean isFull() { return deque.size() >= capacity; }
    public boolean isEmpty() { return deque.isEmpty(); }


     //Adds a new FoodItem at the front of the deque.

    public void add(FoodItem item) {
        if (isFull())
            throw new IllegalStateException("Storage is full (capacity " + capacity + ").");
        deque.addFirst(item); // always add from the front
    }

    //Remove a FoodItem based on the current mode
    public FoodItem remove() {
        if (isEmpty())
            throw new NoSuchElementException("Storage is empty.");

        if (mode == Mode.STACK_FRONT_BOTH)
            return deque.removeFirst();  // LIFO to remove from same side
        else
            return deque.removeLast();   // FIFO to remove from opposite side
    }

    // Peek the FoodItem at the top/front without removing it

    public FoodItem peekTop() {
        if (isEmpty())
            throw new NoSuchElementException("Storage is empty.");
        return deque.peekFirst(); // just look at the front item
    }

    // Display all items currently stored
    public String displayAll() {
        if (isEmpty())
            return "Storage is empty.";

        StringBuilder sb = new StringBuilder("Storage contents (front to back):\n");
        int i = 0;
        for (FoodItem f : deque)
            sb.append("[" + i++ + "] " + f + "\n");
        return sb.toString();
    }

    // Search for a specific food by name

    public FoodItem searchByName(String name) {
        for (FoodItem f : deque)
            if (f.getName().equalsIgnoreCase(name))
                return f;
        return null;
    }

    // Search by the weight searchByWeightRange
    public String searchByWeightRange(int min, int max) {
        StringBuilder sb = new StringBuilder();
        int found = 0;

        for (FoodItem f : deque) {
            if (f.getWeightGrams() >= min && f.getWeightGrams() <= max) {
                sb.append(f).append("\n");
                found++;
            }
        }

        return found == 0 ? "Sorry, no items in that weight range." : sb.toString();
    }

    //Search by best-before date range
    public String searchByBestBeforeRange(LocalDate from, LocalDate to) {
        StringBuilder sb = new StringBuilder();
        int found = 0;

        for (FoodItem f : deque) {
            LocalDate bb = f.getBestBefore();
            // this is to include items equal to or between both dates

            if ((bb.isEqual(from) || bb.isAfter(from)) &&
                (bb.isEqual(to) || bb.isBefore(to))) {
                sb.append(f).append("\n");
                found++;
            }
        }

        return found == 0 ? "Sorry, no items in that best-before range." : sb.toString();
    }
}

