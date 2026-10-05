package lambdas.comparators;

import java.util.Comparator;
import java.util.LinkedHashMap;

import java.util.Map;

/**
 * A book with a title, an author and a publication year.
 *
 * Its natural order (from compareTo) is by title, then author. For any other
 * order, use one of the Comparator fields, or look one up by name in comparators.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class Book implements Comparable<Book> {

    private String title;
    private String author;
    private int publicationYear;

    /**
     * Creates a book.
     *
     * @param title           the book's title
     * @param author          the author's name
     * @param publicationYear the year it was published
     */
    public Book(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public String getTitle()          { return title; }

    /**
     * Returns the author's name.
     *
     * @return the author's name
     */
    public String getAuthor()         { return author; }

    /**
     * Returns the year the book was published.
     *
     * @return the year the book was published
     */
    public int getPublicationYear()   { return publicationYear; }

    // ---------------------------------------------------------------
    // Natural order (Comparable): alphabetical by title, then author
    // ---------------------------------------------------------------
    /**
     * Orders books by title, and breaks ties by author.
     *
     * @param b the book to compare with
     * @return a negative number if this book comes first, a positive number
     *         if b comes first, or 0 if they're tied
     */
    @Override
    public int compareTo(Book b) {
        int result = this.title.compareTo(b.getTitle());
        if (result != 0) {
            return result;
        } else {
            return this.author.compareTo(b.getAuthor());
        }
    }

    // ---------------------------------------------------------------
    // Other orderings (Comparator), written as lambdas
    // ---------------------------------------------------------------
    /** Oldest first. */
    static Comparator<Book> sortByYearAsc =
        (b1, b2) -> b1.getPublicationYear() - b2.getPublicationYear();

    /** Newest first. */
    static Comparator<Book> sortByYearDesc =
        (b1, b2) -> b2.getPublicationYear() - b1.getPublicationYear();

    /** A to Z by author. */
    static Comparator<Book> sortByAuthor =
        (b1, b2) -> b1.getAuthor().compareTo(b2.getAuthor());

    /** Newest first. Books from the same year go A to Z by title. */
    static Comparator<Book> sortByYearDescTitle =
        (b1, b2) -> b2.getPublicationYear() - b1.getPublicationYear() == 0
            ? b1.getTitle().compareTo(b2.getTitle())
            : b2.getPublicationYear() - b1.getPublicationYear();

    /**
     * Every comparator above, looked up by a short name such as "yearDesc".
     * get returns null for a name that isn't in the map.
     */
    // LinkedHashMap keeps insertion order so the menu prints in a predictable
    // order (a plain HashMap works too, but its key order is arbitrary).
    static Map<String, Comparator<Book>> comparators = new LinkedHashMap<>();

    static {
        comparators.put("yearAsc", sortByYearAsc);
        comparators.put("yearDesc", sortByYearDesc);
        comparators.put("author", sortByAuthor);
        comparators.put("yearDescTitle", sortByYearDescTitle);
    }

    /**
     * Returns the title, author and year lined up in columns.
     *
     * @return one formatted line describing this book
     */
    @Override
    public String toString() {
        return String.format("%-28s %-22s %d", title, author, publicationYear);
    }
}
