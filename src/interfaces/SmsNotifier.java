package interfaces;

/**
 * Sends messages as text messages.
 * Anything longer than MAX_LENGTH characters is cut off.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class SmsNotifier implements Notifier {
    /**
     * Prints the message as a text, cut off at MAX_LENGTH characters.
     *
     * @param message the text to send
     */
    @Override
    public void send(String message) {
        String text = message.length() > MAX_LENGTH
                ? message.substring(0, MAX_LENGTH)
                : message;
        System.out.println("  [sms] " + text);
    }
}
