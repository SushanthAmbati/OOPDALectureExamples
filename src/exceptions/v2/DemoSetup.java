package exceptions.v2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * SETUP ONLY
 * Creates an "images" folder next to where you run the program, containing:
 *   kuchen.gif  - a real (non-empty) file: "cake" in German
 *   cookie.png  - an EMPTY file, standing in for a corrupt image
 * There is deliberately no cake.png, so the search has to work for it.
 */
public class DemoSetup {

    public static void createSampleImages() {
        try {
            Path folder = Path.of("images");
            Files.createDirectories(folder);
            Files.write(folder.resolve("kuchen.gif"), "GIF89a-pretend-image-data".getBytes());
            Files.write(folder.resolve("cookie.png"), new byte[0]);
        } catch (IOException e) {
            System.out.println("Setup failed: " + e.getMessage());
        }
    }
}
