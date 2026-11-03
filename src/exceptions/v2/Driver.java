package exceptions.v2;
import java.io.IOException;

/**
 * VERSION 2: custom exceptions.
 *
 * Same call chain as V1:  Driver -> LanguageManipulator -> ExtensionManipulator -> FileOpener
 * But now the Driver gets a different exception TYPE for each kind of failure,
 * so it can give the user a different, accurate response for each one.
 */
public class Driver {

    public static void main(String[] args) {
        DemoSetup.createSampleImages();

        LanguageManipulator finder = new LanguageManipulator();

        search(finder, "cake.png");     // 1) found in another language
        search(finder, "pie.png");      // 2) doesn't exist anywhere
        search(finder, "cookie.png");   // 3) exists but is broken - now reported correctly
    }

    private static void search(LanguageManipulator finder, String request) {
        System.out.println("==================================================");
        System.out.println("[Driver] user asked for " + request);

        try {
            OpenedImage image = finder.find(request);
            System.out.println("[Driver] SUCCESS: opened " + image.fileName()
                    + " (" + image.bytes().length + " bytes)");

        } catch (CorruptImageException e) {
            // One catch block per problem, each with its own response.
            System.out.println("[Driver] Found " + e.getFileName()
                    + ", but it's damaged. Please upload it again.");

        } catch (ImageNotFoundException e) {
            System.out.println("[Driver] Sorry, no image for \"" + e.getSearchTerm() + "\".");
            System.out.println("[Driver] Files tried: " + e.getAttemptedFiles());

        } catch (IOException e) {
            // Anything else we didn't predict (e.g. no permission to read the folder).
            System.out.println("[Driver] Unexpected problem reading files: " + e.getMessage());
        }
    }
}
