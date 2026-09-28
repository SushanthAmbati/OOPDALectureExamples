package InterfaceLecture;

interface Notifier {

    // Implicitly public static final. Interfaces can hold constants.
    public int MAX_LENGTH = 160;

    // Implicitly public abstract. No body, no state.
    public void send(String message);

    // A default method: a real implementation supplied by the interface.
    // Available to every implementer without being rewritten.
    public default void sendAll(String[] messages) {
        for (String m : messages) {
            send(m);
        }
    }
}
