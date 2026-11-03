package exceptions.v2;
import java.util.List;

/**
 * CUSTOM EXCEPTION #2: nothing matched.
 *
 * Why not just FileNotFoundException?  Because it only carries a message String.
 * This one carries real DATA - the list of every file name that was tried -
 * so the Driver can use it with a getter instead of picking apart a string.
 */
@SuppressWarnings("serial")   // all exceptions are Serializable; not covered in this course
public class ImageNotFoundException extends Exception {

    private final String searchTerm;
    private final List<String> attemptedFiles;

    public ImageNotFoundException(String searchTerm, List<String> attemptedFiles) {
        super("no image found for \"" + searchTerm + "\" (" + attemptedFiles.size() + " files tried)");
        this.searchTerm = searchTerm;
        this.attemptedFiles = List.copyOf(attemptedFiles);   // defensive copy - nobody can change it later
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public List<String> getAttemptedFiles() {
        return attemptedFiles;
    }
}
