package lambdas.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.Scanner;

/**
 * Sorts the same list of books in several different orders.
 * Sections 2 to 4 of main are commented out. Turn them on one at a time.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class BookDemo {

    /** The books being sorted. */
    static List<Book> bookSet = new ArrayList<Book>();

    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        populateCollections();

        // 1. Original insertion order
        System.out.println("=== Original order ===");
        displayBooks();

        bookSet.sort(Book.comparators.get("yearDesc"));
        System.out.println("\n=== Comparator: yearDesc ===");
        displayBooks();

        
        // 2. Natural order: uses Book.compareTo (Comparable)
        /*bookSet.sort(null);   // null means "use the natural ordering"
        System.out.println("\n=== Natural order (Comparable: title, then author) ===");
        displayBooks();*/
        
        // 3. Every Comparator in the map, one after another
        /*for (String name : Book.comparators.keySet()) {
            bookSet.clear();
            populateCollections();
            //bookSet.sort(Book.comparators.get(name));
            //System.out.println("\n=== Comparator: " + name + " ===");
            displayBooks();
        }*/

        
        
        
        // 4. Let the user pick one
        /*Comparator<Book> comparator = getComparatorChoice();
        if (comparator == null) {
            System.out.println("No such comparator.");
            return;
        }
        bookSet.sort(comparator);
        System.out.println("\n=== Your choice ===");
        displayBooks();*/
    }

    /**
     * Adds five sample books to bookSet.
     */
    private static void populateCollections() {
        bookSet.add(new Book("Java Basics",       "Smith",   2015));
        bookSet.add(new Book("Data Structures",   "Nguyen",  2020));
        bookSet.add(new Book("Operating Systems", "Garcia",  2008));
        bookSet.add(new Book("Algorithms",        "Lee",     2020));
        bookSet.add(new Book("Java Basics",       "Adams",   2015));
    }

    /**
     * Prints every book in bookSet, one per line.
     */
    private static void displayBooks() {
        for (Book b : bookSet) {
            System.out.println("  " + b);
        }
    }

    /**
     * Lists the comparator names and asks the user to type one.
     *
     * @return the matching comparator, or null if the name doesn't exist
     */
    private static Comparator<Book> getComparatorChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nAvailable comparators:");
        for (String key : Book.comparators.keySet()) {
            System.out.println("  " + key);
        }
        System.out.print("Choose your sorting comparator: ");
        String choice = sc.next();
        return Book.comparators.get(choice);
    }
}
