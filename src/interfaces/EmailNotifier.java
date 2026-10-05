package interfaces;

/**
 * Sends messages by email.
 * It also has getInboxSize, which isn't part of Notifier. You can only call it
 * through a variable whose type is EmailNotifier.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class EmailNotifier implements Notifier {
    private int inboxSize = 42;

    /**
     * Prints the message as an email.
     *
     * @param message the text to send
     */
    @Override
    public void send(String message) {
        System.out.println("  [email] " + message);
    }

    /**
     * Returns how many messages are in the inbox.
     *
     * @return the number of messages in the inbox
     */
    // This method exists ONLY on EmailNotifier, not on the interface.
    public int getInboxSize() {
        return inboxSize;
    }
}
