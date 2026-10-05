package interfaces;

/**
 * Walks through static vs. dynamic types using Notifier.
 * Each numbered section in main matches a heading in the output.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class NotifierDemo {

    // Two overloads. Which one the compiler picks is decided by the
    // STATIC type of the argument, not by the object passed in.
    /**
     * Chosen when the argument's static type is Notifier.
     *
     * @param n the notifier being logged
     */
    static void log(Notifier n) {
        System.out.println("  log(Notifier) was chosen");
    }

    /**
     * Chosen when the argument's static type is EmailNotifier.
     *
     * @param e the email notifier being logged
     */
    static void log(EmailNotifier e) {
        System.out.println("  log(EmailNotifier) was chosen");
    }

    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Static type: Notifier.   Dynamic type: EmailNotifier.
        Notifier notifier = new EmailNotifier();

        System.out.println("1. Dynamic dispatch");
        notifier.send("Assignment posted");        // EmailNotifier.send runs
        System.out.println("   declared as Notifier, actually a " + notifier.getClass().getSimpleName());

        System.out.println("\n2. The static type limits what you may call");
        //System.out.println(notifier instanceof Notifier);  // true
        System.out.println("   inbox size = " + ((EmailNotifier)notifier).getInboxSize());
        //notifier.getInboxSize();
        // ^ What happens here?
    

        System.out.println("\n3. Casting narrows the static type");
        if (notifier instanceof EmailNotifier) {           // always guard a cast
            EmailNotifier email = (EmailNotifier) notifier; // static type is now EmailNotifier
            System.out.println("   inbox size = " + email.getInboxSize());
        }

        System.out.println("\n4. The dynamic type can change; the static type cannot");
        notifier = new SmsNotifier();              // same variable, new dynamic type
        notifier.send("Now the SMS version runs");

        System.out.println("\n5. Overriding is dynamic, overloading is static");
        EmailNotifier concrete = new EmailNotifier();
        Notifier widened = concrete;               // same object, wider static type
        log(concrete);                             // log(EmailNotifier)
        log(widened);              // log(Notifier)  <-- same object!

        System.out.println("\n6. One interface type, many implementations");
        Notifier[] channels = { new EmailNotifier(), new SmsNotifier() };
        for (Notifier channel : channels) {
            channel.send("Quiz Friday");           // each picks its own body
        }

        System.out.println("\n7. Default method inherited from the interface");
        channels[1].sendAll(new String[] { "Reminder A", "Reminder B" });

        // Notifier n = new Notifier();
        // ^ Uncomment: COMPILE ERROR. An interface cannot be instantiated.
    }
}
