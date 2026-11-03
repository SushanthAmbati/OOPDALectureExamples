package exceptions.v1;
import java.io.FileNotFoundException;

/**
 * VERSION 1: built-in exceptions only.
 *
 * Call chain:  Driver -> LanguageManipulator -> ExtensionManipulator -> FileOpener
 * Exceptions travel back UP that chain until someone catches them.
 * The Driver is the last stop: if the search fails, it decides what to tell the user.
 */
public class Driver {

    public static void main(String[] args) {
        DemoSetup.createSampleImages();

        LanguageManipulator finder = new LanguageManipulator();

        search(finder, "cake.png");     // 1) no cake.png, but it exists in another language
        search(finder, "pie.png");      // 2) doesn't exist anywhere
        search(finder, "cookie.png");   // 3) cookie.png IS in the folder... watch what happens
    }

    private static void search(LanguageManipulator finder, String request) {
        System.out.println("==================================================");
        System.out.println("[Driver] user asked for " + request);

        try {
            OpenedImage image = finder.find(request);
            System.out.println("[Driver] SUCCESS: opened " + image.fileName()
                    + " (" + image.bytes().length + " bytes)");
        } catch (FileNotFoundException e) {
            // All the Driver gets is ONE type and ONE message string.
            //  - Which files were tried?  It would have to pick apart the string.
            //  - Was the file missing, or broken?  It can't tell.
            System.out.println("[Driver] Sorry, " + e.getMessage());
        }
    }
}
