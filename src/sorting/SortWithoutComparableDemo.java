package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shows what goes wrong when you sort objects that have no natural order,
 * and two ways to fix it.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class SortWithoutComparableDemo {
    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        BookWithoutComparable[] books = {
            new BookWithoutComparable("Java Basics", "Smith"),
            new BookWithoutComparable("Algorithms", "Lee"),
            new BookWithoutComparable("Java Basics", "Adams")
        };

        System.out.println("1. Arrays.sort with no natural order: compiles, then CRASHES when run");
        // Arrays.sort(books);
        // ^ Uncomment: it COMPILES (Arrays.sort accepts any array), but the program
        //   CRASHES with a ClassCastException. While sorting, Java tries to treat
        //   each book as a Comparable, and BookWithoutComparable isn't one.
        System.out.println("   (see the commented-out line in the source)");

        System.out.println("\n2. Collections.sort is stricter: it will not even compile");
        List<BookWithoutComparable> bookList = new ArrayList<>(Arrays.asList(books));
        // Collections.sort(bookList);
        // ^ Uncomment (and import java.util.Collections): COMPILE ERROR.
        //   Collections.sort requires the element type to implement Comparable.
        System.out.println("   (see the commented-out line in the source)");

        System.out.println("\n3. Fix #1: hand sort() a Comparator that says how to compare");
        Arrays.sort(books, (b1, b2) -> b1.getTitle().compareTo(b2.getTitle()));
        for (BookWithoutComparable b : books) {
            System.out.println("   " + b);
        }

        System.out.println("\n4. Fix #2: give the class a natural order (implements Comparable)");
        System.out.println("   That is exactly what Book does. Run BookDemo to see it.");
    }
}
