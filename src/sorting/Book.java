package sorting;

/**
 * A book that knows how to compare itself to another book.
 * Because of that, Arrays.sort can put an array of them in order.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class Book implements Comparable<Book> {
    private String title;
    private String author;

    /**
     * Creates a book.
     *
     * @param title  the book's title
     * @param author the author's name
     */
    public Book(String title, String author) {
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
     * Orders books by title, and breaks ties by author.
     *
     * @param b the book to compare with
     * @return a negative number if this book comes first, a positive number
     *         if b comes first, or 0 if they're tied
     */
    @Override
    public int compareTo(Book b) {
        // Compare alphabetically by title, then by author
        int result = this.title.compareTo(b.getTitle());
        if (result != 0) {
            return result;
        } else {
            return this.author.compareTo(b.getAuthor());
        }
    }

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
