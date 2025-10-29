import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
Main class

This class handles user interaction (menu, inputs, and commands)
and connects everything together:
FoodItem objects (individual food trays)
Storage (the unit that holds them)

The app will simulate how a fast-food restaurant might manage
trays are going to be added and removed using stack (LIFO).
 */
public class Main {


    private static final int CAPACITY = 8;  //Maximum number of trays that will be allowed in storage

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage(CAPACITY, Storage.Mode.STACK_FRONT_BOTH);

        System.out.println("___ Welcome to Fast-Food Storage Manager___");
        System.out.println("Managing your trays in the best way possible!\n");

        boolean running = true;
        while (running) {
            // That will display the main menu
            System.out.println("\n Your current mode: " + storage.getMode());
            System.out.println("1) Switch storage type");
            System.out.println("2) Add a new food item");
            System.out.println("3) Remove a food item");
            System.out.println("4) Peek at the top item");
            System.out.println("5) Display all items");
            System.out.println("6) Search item by name");
            System.out.println("7) Search items by weight ");
            System.out.println("8) Search items by best-before date ");
            System.out.println("9) Exit the program");
            System.out.print("→ Choose an option: ");

            String opt = sc.nextLine().trim();

            try {
                switch (opt) {

                    // Option 1 is Switching between LIFO or FIFO behavior
                    case "1":
                        System.out.println("\nSelect mode: ");
                        System.out.println("1) STACK_FRONT_BOTH (LIFO / stack behavior)");
                        System.out.println("2) ADD_FRONT_REMOVE_OPP (FIFO / queue behavior)");
                        System.out.print(" Option: ");
                        String m = sc.nextLine().trim();

                        if (m.equals("1"))
                            storage.setMode(Storage.Mode.STACK_FRONT_BOTH);
                        else
                            storage.setMode(Storage.Mode.ADD_FRONT_REMOVE_OPP);

                        System.out.println(" Mode switched to: " + storage.getMode());
                        break;

                    // Option 2 will Add new food tray to the storage
                    case "2":
                        if (storage.isFull()) {
                            System.out.println("  Storage is full! Remove something first please.");
                            break;
                        }

                        System.out.print("Enter food name: ");
                        String name = sc.nextLine().trim();
                        if (name.isEmpty()) {
                            System.out.println("  Name cannot be empty, try again.");
                            break;
                        }

                        System.out.print("Enter weight (grams): ");
                        int weight = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter best-before date (YYYY-MM-DD, must be within 14 days): ");
                        LocalDate bb = LocalDate.parse(sc.nextLine().trim());
                        LocalDateTime placed = LocalDateTime.now();

                        // Validate that the date is not too far ahead
                        if (bb.isAfter(placed.toLocalDate().plusDays(14))) {
                            System.out.println("  Best-before date too far ahead (max 14 days allowed).");
                            break;
                        }

                        storage.add(new FoodItem(name, weight, bb, placed));
                        System.out.println(" Item added !");
                        break;

                    // Option 3 will Remove a food tray
                    case "3":
                        FoodItem removed = storage.remove();
                        System.out.println("  Removed: " + removed);
                        break;

                    // Option 4 is to Peek at the top tray without removing it
                    case "4":
                        System.out.println(" Top item: " + storage.peekTop());
                        break;

                    // Option 5 will Display every tray currently stored ---
                    case "5":
                        System.out.println(storage.displayAll());
                        break;

                    // Option 6 will Search a tray by its name
                    case "6":
                        System.out.print("Enter name to search: ");
                        String searchName = sc.nextLine().trim();
                        FoodItem found = storage.searchByName(searchName);
                        System.out.println(found == null ? " Item not found!" : found);
                        break;

                    // Option 7 is to search by weight range
                    case "7":
                        System.out.print("Enter minimum weight: ");
                        int min = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter maximum weight: ");
                        int max = Integer.parseInt(sc.nextLine().trim());
                        System.out.println(storage.searchByWeightRange(min, max));
                        break;

                    // Option 8 will search by best-before date range
                    case "8":
                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        LocalDate from = LocalDate.parse(sc.nextLine().trim());
                        System.out.print("Enter end date (YYYY-MM-DD): ");
                        LocalDate to = LocalDate.parse(sc.nextLine().trim());
                        System.out.println(storage.searchByBestBeforeRange(from, to));
                        break;

                    // Option 9 will finalise the program
                    case "9":
                        running = false;
                        System.out.println("\n Exiting... Thank you for using Fast-Food Storage Manager!");
                        break;

                    // Invalid input message
                    default:
                        System.out.println(" Invalid option. Please  try again, and choose from 1 to 9.");
                }

            // Error for exceptions
            } catch (IllegalStateException | NoSuchElementException ex) {
                System.out.println("  Error: " + ex.getMessage());
            } catch (DateTimeParseException | NumberFormatException ex) {
                System.out.println("  Invalid input format. Please try again.");
            }
        }

        //  exit
        sc.close();
        System.out.println(" Program Finished .");
    }
}


