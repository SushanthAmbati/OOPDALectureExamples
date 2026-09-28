package InterfaceLecture;

class SmsNotifier implements Notifier {
    @Override
    public void send(String message) {
        String text = message.length() > MAX_LENGTH
                ? message.substring(0, MAX_LENGTH)
                : message;
        System.out.println("  [sms] " + text);
    }
}
