package exceptions.v1;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * TOP SEARCH LEVEL: tries the original word first. If that fails, it
 * tries the word in other languages: pastel, gateau, kuchen ...
 * Each attempt is handed to ExtensionManipulator.
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
     * @throws FileNotFoundException if every language and extension failed
     */
    public OpenedImage find(String fileName) throws FileNotFoundException {
        String word = removeExtension(fileName);

        try {                                                        // OUTER try
            System.out.println("  [LanguageManipulator] trying original word: " + word);
            return extensions.find(word);

        } catch (FileNotFoundException original) {                   // OUTER catch
            // The original word failed. Now try each translation.
            for (String translated : TRANSLATIONS.getOrDefault(word, List.of())) {
                try {                                                // NESTED try (inside a catch)
                    System.out.println("  [LanguageManipulator] trying translation: " + translated);
                    return extensions.find(translated);
                } catch (FileNotFoundException e) {                  // NESTED catch
                    System.out.println("  [LanguageManipulator] " + e.getMessage());
                }
            }

            // THROW: nothing worked in any language. Let the Driver deal with it.
            throw new FileNotFoundException("could not find \"" + fileName
                    + "\" in any language or extension");
        }
    }

    private String removeExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return (dot == -1) ? fileName : fileName.substring(0, dot);
    }
}
