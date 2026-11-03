package exceptions.v2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * LOWEST LEVEL: opens exactly one file. Nothing else.
 *
 * CHANGED FROM V1: a broken file now throws CorruptImageException
 * instead of a generic IOException.
 */
public class FileOpener {

    private final Path folder = Path.of("images");

    /**
     * Opens a single file from the images folder.
     *
     * @param fileName the exact file name, e.g. "cake.png"
     * @return the opened image
     * @throws java.nio.file.NoSuchFileException if the file does not exist
     * @throws IOException if the file can't be read for some other reason
     * @throws CorruptImageException if the file exists but is empty
     */
    public OpenedImage open(String fileName) throws IOException, CorruptImageException {
        System.out.println("      [FileOpener] trying " + fileName);

        // Throws NoSuchFileException (a subclass of IOException) if missing.
        byte[] bytes = Files.readAllBytes(folder.resolve(fileName));

        if (bytes.length == 0) {
            throw new CorruptImageException(fileName);     // THROW our own, specific type
        }

        return new OpenedImage(fileName, bytes);
    }
}
