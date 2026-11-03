package exceptions.v1;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * MIDDLE LEVEL: given a word like "cake", tries it with different extensions:
 * cake.png, cake.jpg, cake.gif ...
 *
 * When FileOpener fails, this class CATCHES the exception and recovers
 * by trying the next extension. If every extension fails, it gives up and
 * THROWS a new exception to its own caller.
 */
public class ExtensionManipulator {

    private static final String[] EXTENSIONS = {"png", "jpg", "gif"};

    private final FileOpener opener = new FileOpener();

    /**
     * @param word a file name without its extension, e.g. "cake"
     * @return the first file that opened successfully
     * @throws FileNotFoundException if no extension worked
     */
    public OpenedImage find(String word) throws FileNotFoundException {
        for (String ext : EXTENSIONS) {
            try {
                return opener.open(word + "." + ext);           // success -> leave immediately
            } catch (IOException e) {
                // RECOVERY: this extension failed, so loop around and try the next one.
                //
                // !! Look closely: this catches EVERY IOException - a missing file
                // AND a file that exists but is broken. They all look the same here.
                System.out.println("      [ExtensionManipulator] failed: "
                        + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }

        // THROW: every extension failed. Report it upward with a new exception.
        throw new FileNotFoundException("no extension worked for \"" + word + "\"");
    }
}
