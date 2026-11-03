package exceptions.v2;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

/**
 * MIDDLE LEVEL: given a word like "cake", tries cake.png, cake.jpg, cake.gif ...
 *
 * CHANGED FROM V1:
 *  - It catches ONLY NoSuchFileException ("missing"), because that is the only
 *    problem trying another extension can fix.
 *  - CorruptImageException is NOT caught here. It passes straight up the chain,
 *    because ExtensionManipulator can't fix a broken file.
 *  - When everything fails, it throws ImageNotFoundException with the list of names tried.
 */
public class ExtensionManipulator {

    private static final String[] EXTENSIONS = {"png", "jpg", "gif"};

    private final FileOpener opener = new FileOpener();

    /**
     * @param word a file name without its extension, e.g. "cake"
     * @return the first file that opened successfully
     * @throws ImageNotFoundException if no extension exists
     * @throws CorruptImageException if a matching file exists but is broken
     * @throws IOException for any other read problem
     */
    public OpenedImage find(String word)
            throws ImageNotFoundException, CorruptImageException, IOException {

        List<String> attempted = new ArrayList<>();

        for (String ext : EXTENSIONS) {
            String candidate = word + "." + ext;
            try {
                return opener.open(candidate);
            } catch (NoSuchFileException e) {
                // Catch ONLY what we can fix: missing -> try the next extension.
                System.out.println("      [ExtensionManipulator] missing: " + candidate);
                attempted.add(candidate);
            }
        }

        throw new ImageNotFoundException(word, attempted);
    }
}
