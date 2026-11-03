package exceptions.v1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * LOWEST LEVEL: opens exactly one file. Nothing else.
 *
 * It does NOT try to recover from problems. It has no idea what the caller
 * wants to do about a missing file, so it just reports the problem upward.
 */
public class FileOpener {

    private final Path folder = Path.of("images");

    /**
     * Opens a single file from the images folder.
     *
     * @param fileName the exact file name, e.g. "cake.png"
     * @return the opened image
     * @throws IOException if the file is missing, unreadable, or empty
     */
    public OpenedImage open(String fileName) throws IOException {      // THROWS: "this might fail; caller, deal with it"
        System.out.println("      [FileOpener] trying " + fileName);

        // Files.readAllBytes throws NoSuchFileException (a kind of IOException)
        // if the file does not exist. We don't catch it - it passes straight up.
        byte[] bytes = Files.readAllBytes(folder.resolve(fileName));

        if (bytes.length == 0) {
            // THROW: we detected a problem ourselves. There is no built-in
            // "CorruptImageException", so the best we can do is a plain IOException.
            throw new IOException(fileName + " exists but is empty/corrupt");
        }

        return new OpenedImage(fileName, bytes);
    }
}
