package sorting;

/**
 * The same data as Book, but this class does NOT implement Comparable.
 * Java can't tell which of two of these comes first, so it can't sort them
 * on its own. See SortWithoutComparableDemo.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class BookWithoutComparable {
    private String title;
    private String author;

    /**
     * Creates a book.
     *
     * @param title  the book's title
     * @param author the author's name
     */
    public BookWithoutComparable(String title, String author) {
        this.title = title;
        this.author = author;
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public String getTitle()  { return title; }

    /**
     * Returns the author's name.
     *
     * @return the author's name
     */
    public String getAuthor() { return author; }

    /**
     * Returns the book as "title by author".
     *
     * @return a short description of this book
     */
    @Override
    public String toString() {
        return title + " by " + author;
    }
}
