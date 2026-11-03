package exceptions.v2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TOP SEARCH LEVEL: tries the original word, then translations.
 *
 * CHANGED FROM V1: it collects the attempted file names from every
 * ImageNotFoundException it catches, so the final exception carries
 * the complete list. It still doesn't catch CorruptImageException.
 */
public class LanguageManipulator {

    private static final Map<String, List<String>> TRANSLATIONS = Map.of(
            "cake",   List.of("pastel", "gateau", "kuchen"),    // Spanish, French, German
            "cookie", List.of("galleta", "biscuit", "keks")
    );

    private final ExtensionManipulator extensions = new ExtensionManipulator();

    /**
     * @param fileName what the user asked for, e.g. "cake.png"
     * @return the first matching image in any language and extension
     * @throws ImageNotFoundException if nothing matched; lists every file tried
     * @throws CorruptImageException if a matching file exists but is broken
     * @throws IOException for any other read problem
     */
    public OpenedImage find(String fileName)
            throws ImageNotFoundException, CorruptImageException, IOException {

        String word = removeExtension(fileName);

        try {                                                         // OUTER try
            System.out.println("  [LanguageManipulator] trying original word: " + word);
            return extensions.find(word);

        } catch (ImageNotFoundException original) {                   // OUTER catch
            List<String> allAttempts = new ArrayList<>(original.getAttemptedFiles());

            for (String translated : TRANSLATIONS.getOrDefault(word, List.of())) {
                try {                                                 // NESTED try
                    System.out.println("  [LanguageManipulator] trying translation: " + translated);
                    return extensions.find(translated);
                } catch (ImageNotFoundException e) {                  // NESTED catch
                    allAttempts.addAll(e.getAttemptedFiles());        // use the DATA, not the message
                }
            }

            throw new ImageNotFoundException(fileName, allAttempts);
        }
    }

    private String removeExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return (dot == -1) ? fileName : fileName.substring(0, dot);
    }
}
