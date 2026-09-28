package InterfaceLecture;


class EmailNotifier implements Notifier {
    private int inboxSize = 42;

    @Override
    public void send(String message) {
        System.out.println("  [email] " + message);
    }

    // This method exists ONLY on EmailNotifier, not on the interface.
    public int getInboxSize() {
        return inboxSize;
    }
}
