package exceptions.v2;
/**
 * The result of a successful search: which file was opened and its contents.
 * (A record is just a compact class that holds data: Java 16+.)
 */
public record OpenedImage(String fileName, byte[] bytes) {
}
