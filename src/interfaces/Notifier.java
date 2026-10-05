package interfaces;

/**
 * Anything that can send a text message to someone.
 * A class that implements this only has to write send. It gets sendAll for free.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
interface Notifier {

    /** The longest message an SMS can carry. */
    // Implicitly public static final. Interfaces can hold constants.
    public int MAX_LENGTH = 160;

    /**
     * Sends one message.
     *
     * @param message the text to send
     */
    // Implicitly public abstract. No body, no state.
    public void send(String message);

    /**
     * Sends each message in order by calling send on each one.
     *
     * @param messages the texts to send
     */
    // A default method: a real implementation supplied by the interface.
    // Available to every implementer without being rewritten.
    public default void sendAll(String[] messages) {
        for (String m : messages) {
            send(m);
        }
    }
}
