package sorting;

import java.util.Arrays;

/**
 * Sorts an array of Book objects.
 * This works because Book implements Comparable.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class BookDemo {
    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Book[] books = {
            new Book("Java Basics", "Smith"),
            new Book("Algorithms", "Lee"),
            new Book("Java Basics", "Adams")
        };

        Arrays.sort(books);   // works because Book implements Comparable<Book>
        //compareTo is called on individually instead of the whole array
        //System.out.println(books[0].compareTo(books[1]));

        for (Book b : books) {
            System.out.println(b);
        }
    }
}
