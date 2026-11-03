package exceptions.v2;
/**
 * CUSTOM EXCEPTION #1: the file EXISTS, but it is broken.
 *
 * Why not just IOException?  Because then a catch block can't tell
 * "broken file" apart from "missing file" - and those need different handling.
 * Having its own TYPE means a catch block can target exactly this problem.
 *
 * It extends Exception (not RuntimeException), so it is a CHECKED exception:
 * a corrupt file is a problem from the environment, not a programming bug,
 * so the compiler should force callers to plan for it.
 */
@SuppressWarnings("serial")   // all exceptions are Serializable; not covered in this course
public class CorruptImageException extends Exception {

    private final String fileName;     // extra data a plain IOException can't carry

    public CorruptImageException(String fileName) {
        super(fileName + " exists but is empty/corrupt");   // the message, stored by Throwable
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
